package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.Rule;
import it.unicam.cs.ids.lp.rules.RulesEnum;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRequest;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRule;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleMapper;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleRepository;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRule;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
    private CashbackRuleMapper cashbackRuleMapper;

    @Test
    void applyCoupons() {
        Customer customer = new Customer();
        customerRepository.save(customer);

        CouponRequest couponRequest = new CouponRequest(Set.of(RulesEnum.CASHBACK), null);
        Coupon coupon = couponService.createCoupon(customer.getId(), couponRequest);

        Product product = new Product();
        product.setPrice(100);
        productRepository.save(product);

        CashbackRequest cashbackRequest = new CashbackRequest(Set.of(product), 5);
        CashbackRule cashbackRule = cashbackRuleMapper.apply(cashbackRequest);
        CouponRule couponRule = new CouponRule();
        couponRule.setCoupon(coupon);
        couponRuleRepository.save(couponRule);
        cashbackRule.setPlatformRule(couponRule);
        cashbackRuleRepository.save(cashbackRule);

        CustomerOrder order = new CustomerOrder();
        order.setProducts(Set.of(product));

        List<String> strings = couponService.applyCoupons(Set.of(coupon.getId()), order);
        Assertions.assertFalse(strings.isEmpty());
        Assertions.assertEquals("CashbackRule   5", strings.get(0));
        Assertions.assertFalse(couponRepository.existsById(coupon.getId()));
    }

    @Test
    void createCoupon() {
        Customer customer = new Customer();
        customerRepository.save(customer);

        CouponRequest couponRequest = new CouponRequest(Set.of(RulesEnum.CASHBACK), null);
        couponService.createCoupon(customer.getId(), couponRequest);

        Assertions.assertNotNull(customer.getCoupons());
        Assertions.assertFalse(customer.getCoupons().isEmpty());
        CouponRule couponRule = couponRuleRepository.findByCoupon(customer.getCoupons().stream().toList().get(0));
        Assertions.assertNotNull(couponRule);
        Assertions.assertTrue(cashbackRuleRepository.findAll()
                .stream()
                .map(Rule::getPlatformRule)
                .anyMatch(abstractPlatformRule -> abstractPlatformRule.equals(couponRule)));
        Assertions.assertNotNull(customer.getCoupons().stream().toList().get(0).getCouponRules());
        Assertions.assertFalse(customer.getCoupons().stream().toList().get(0).getCouponRules().isEmpty());
    }
}
