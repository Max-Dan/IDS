package it.unicam.cs.ids.lp.client.coupon;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRequest;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRule;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleMapper;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleRepository;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRule;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CouponServiceTest {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CashbackRuleRepository cashbackRuleRepository;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CouponRuleRepository couponRuleRepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CashbackRuleMapper cashbackRuleMapper;
    private Coupon coupon;
    private Product product;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customerRepository.save(customer);

        coupon = new Coupon();
        coupon.setCustomer(customer);
        couponRepository.save(coupon);

        product = new Product();
        product.setPrice(100);
        productRepository.save(product);

        CashbackRequest cashbackRequest = new CashbackRequest(Set.of(product), 5);
        CashbackRule cashbackRule = cashbackRuleMapper.apply(cashbackRequest);
        CouponRule couponRule = new CouponRule();
        couponRule.setCoupon(coupon);
        couponRuleRepository.save(couponRule);
        cashbackRule.setPlatformRule(couponRule);
        cashbackRuleRepository.save(cashbackRule);
    }

    @Test
    void applyCoupons() {
        CustomerOrder order = new CustomerOrder();
        order.setProducts(Set.of(product));

        List<String> strings = couponService.applyCoupons(Set.of(coupon.getId()), order);
        strings.forEach(System.out::println);
    }
}
