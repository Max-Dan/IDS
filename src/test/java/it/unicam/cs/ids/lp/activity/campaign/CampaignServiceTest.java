package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
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
import it.unicam.cs.ids.lp.client.card.CustomerCardRequest;
import it.unicam.cs.ids.lp.client.card.CustomerCardService;
import it.unicam.cs.ids.lp.client.card.programs.CashbackData;
import it.unicam.cs.ids.lp.client.card.programs.ProgramData;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.client.order.CustomerOrderMapper;
import it.unicam.cs.ids.lp.client.order.CustomerOrderRepository;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleRequest;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CampaignServiceTest {

    private Activity activity;
    @Autowired
    private CampaignRepository campaignRepository;
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
    private CustomerOrderMapper customerOrderMapper;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private CustomerCardService customerCardService;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CustomerService customerService;

    @BeforeAll
    public void setUp() {
        activity = activityRegistrationService.register(new Activity()).orElseThrow();
        cardService.createCard(activity.getId(), new CardRequest(""));
        productService.createProduct(activity.getId(), new ProductRequest("", 200));
        productService.createProduct(activity.getId(), new ProductRequest("", 600));
        activity = activityRepository.findById(activity.getId()).orElseThrow();
    }

    @Test
    void createCampaign() {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        campaignService.createCampaign(activity.getId(), campaignRequest);
        Assertions.assertTrue(campaignRepository.existsByCard_Activities_Id(activity.getId()));
    }

    @Test
    void modifyCampaign() {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        Campaign campaign = campaignService.createCampaign(activity.getId(), campaignRequest);

        campaignService.modifyCampaign(campaign.getId(), activity.getId(), campaignRequest);
        Assertions.assertEquals("", campaignRepository.findById(campaign.getId()).orElseThrow().getName());
    }

    @Test
    void applyRules() {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        Campaign campaign = campaignService.createCampaign(activity.getId(), campaignRequest);
        Set<Product> products = new HashSet<>(productRepository.findByActivities_Id(activity.getId()));
        CashbackRuleRequest cashbackRequest = new CashbackRuleRequest(products, 5);
        cashbackRuleService.setCampaignCashback(activity.getId(), campaign.getId(), cashbackRequest);

        Customer customer = customerRepository.save(new Customer());
        CustomerOrder order =
                customerOrderMapper.apply(products, customer);
        customerOrderRepository.save(order);
        customerCardService.createCustomerCard(new CustomerCardRequest(customer.getId(), activity.getCard().getId(), false, ""));
        customerService.subscribeToCampaign(customer.getId(), campaign.getId());
        campaignService.applyRules(campaign.getId(), activity.getId(), order);

        List<ProgramData> dataList = customerRepository.findById(customer.getId()).orElseThrow()
                .getCards()
                .stream()
                .flatMap(customerCard -> customerCard.getProgramsData().stream())
                .toList();

        Assertions.assertEquals(1, dataList.size());
        CashbackData cashbackData = (CashbackData) dataList.get(0);
        Assertions.assertEquals((600 + 200) / 100 * 5, cashbackData.getRemainingCashback());
    }

    @Test
    public void getAllCampaigns() {
        CampaignRequest campaignRequest = new CampaignRequest("", LocalDate.EPOCH);
        campaignService.createCampaign(activity.getId(), campaignRequest);
        campaignService.createCampaign(activity.getId(), campaignRequest);
        campaignRequest = new CampaignRequest("", null);
        campaignService.createCampaign(activity.getId(), campaignRequest);

        List<Campaign> allCampaigns = campaignService.getAllCampaigns(activity.getId());
        Assertions.assertEquals(3, allCampaigns.size());
    }
}
