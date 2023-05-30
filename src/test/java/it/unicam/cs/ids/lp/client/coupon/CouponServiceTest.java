package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.card.CardRequest;
import it.unicam.cs.ids.lp.activity.card.CardService;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.activity.product.ProductRequest;
import it.unicam.cs.ids.lp.activity.product.ProductService;
import it.unicam.cs.ids.lp.activity.registration.ActivityMapper;
import it.unicam.cs.ids.lp.activity.registration.ActivityRegistrationService;
import it.unicam.cs.ids.lp.activity.registration.ActivityRequest;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRequest;
import it.unicam.cs.ids.lp.client.card.CustomerCardService;
import it.unicam.cs.ids.lp.client.card.programs.CashbackData;
import it.unicam.cs.ids.lp.client.card.programs.ProgramData;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.client.order.CustomerOrderMapper;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleRequest;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CouponServiceTest {

    private Customer customer;
    private Product product;
    private CustomerCard customerCard;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CashbackRuleService cashbackRuleService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ActivityRegistrationService activityRegistrationService;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private CustomerCardService customerCardService;
    @Autowired
    private CardService cardService;
    @Autowired
    private CustomerOrderMapper customerOrderMapper;

    @BeforeEach
    public void setUp() throws Exception {
        Activity activity = activityMapper.apply(new ActivityRequest(
                "Apple",
                "via california",
                "445-678-9034",
                "test@gmail.com",
                "sonoLaApple"
        ));
        activity = activityRegistrationService.register(activity).orElseThrow();
        cardService.createCard(activity.getId(), new CardRequest(""));
        product = productService.createProduct(activity.getId(), new ProductRequest("", 100));
        customer = customerRepository.save(new Customer());
        customerCard = customerCardService.createCustomerCard(new CustomerCardRequest(customer.getId(), activity.getCard().getId(), false, null));
    }

    @Test
    void applyCoupons() {
        Coupon coupon = couponService.createCoupon(customerCard.getId(), new CouponRequest(LocalDate.EPOCH));
        CashbackRuleRequest cashbackRequest = new CashbackRuleRequest(Set.of(product.getId()), 5);
        cashbackRuleService.setCouponCashback(coupon.getId(), cashbackRequest);

        CustomerOrder order = customerOrderMapper.apply(Set.of(product), customer);

        List<ProgramData> dataList = couponService.applyCoupon(coupon.getId(), order.getProducts().stream().map(Product::getId).collect(Collectors.toSet()));
        //TODO da completare

        Assertions.assertEquals(1, dataList.size());
        CashbackData cashbackData = (CashbackData) dataList.get(0);
        Assertions.assertEquals(5, cashbackData.getRemainingCashback());
    }

    @Test
    void createCoupon() {
        CouponRequest couponRequest = new CouponRequest(null);
        Coupon coupon = couponService.createCoupon(customerCard.getId(), couponRequest);
        Assertions.assertTrue(coupon.getId() != 0);
    }
}
