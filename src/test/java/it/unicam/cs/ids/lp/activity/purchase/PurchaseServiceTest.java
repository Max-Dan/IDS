package it.unicam.cs.ids.lp.activity.purchase;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRequest;
import it.unicam.cs.ids.lp.activity.campaign.CampaignService;
import it.unicam.cs.ids.lp.activity.card.CardRequest;
import it.unicam.cs.ids.lp.activity.card.CardService;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.activity.product.ProductRequest;
import it.unicam.cs.ids.lp.activity.product.ProductService;
import it.unicam.cs.ids.lp.activity.registration.ActivityRegistrationService;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.CustomerService;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCardRequest;
import it.unicam.cs.ids.lp.client.card.CustomerCardService;
import it.unicam.cs.ids.lp.client.card.programs.CashbackData;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleRequest;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;

@SpringBootTest
class PurchaseServiceTest {

    private Activity activity;
    private Campaign campaign;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ActivityRegistrationService activityRegistrationService;
    @Autowired
    private CardService cardService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private CashbackRuleService cashbackRuleService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerCardService customerCardService;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerCardRepository customerCardRepository;

    @BeforeEach
    void setUp() {
        activity = activityRegistrationService.register(new Activity()).orElseThrow();
        cardService.createCard(activity.getId(), new CardRequest(""));
        productService.createProduct(activity.getId(), new ProductRequest("", 500));
        productService.createProduct(activity.getId(), new ProductRequest("", 500));
        campaign = campaignService.createCampaign(activity.getId(), new CampaignRequest("", null));
        activity = activityRepository.findById(activity.getId()).orElseThrow();
    }

    @Test
    void applyBonus() {
        Customer customer = customerRepository.save(new Customer());
        CustomerCard customerCard = customerCardService.createCustomerCard(new CustomerCardRequest(customer.getId(), activity.getCard().getId(), false, null));
        customerService.subscribeToCampaign(customer.getId(), campaign.getId());

        cashbackRuleService.setCampaignCashback(activity.getId(), campaign.getId(),
                new CashbackRuleRequest(productRepository.findByActivity_Id(activity.getId()).stream().map(Product::getId).collect(Collectors.toSet()), 5));

        purchaseService.applyBonus(activity.getId(), customerCard.getId(),
                productRepository.findByActivity_Id(activity.getId()).stream().map(Product::getId).collect(Collectors.toSet()));

        customerCard = customerCardRepository.findById(customerCard.getId()).orElseThrow();
        Assertions.assertFalse(customerCard.getProgramsData().isEmpty());
        Assertions.assertEquals(1000 / 100 * 5, ((CashbackData) customerCard.getProgramsData().stream().findFirst().orElseThrow()).getRemainingCashback());
    }
}
