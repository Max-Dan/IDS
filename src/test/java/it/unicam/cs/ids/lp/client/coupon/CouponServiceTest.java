package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRule;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleRepository;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRule;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
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
    private Coupon coupon;
    private Product product;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customerRepository.save(customer);

        product = new Product();
        product.setPrice(100);
        productRepository.save(product);
        CustomerOrder order = new CustomerOrder();
        order.setProducts(Set.of(product));

        CashbackRule rule = new CashbackRule();
        rule.setCashbackRate(100);
        rule.setProducts(Set.of(product));
        cashbackRuleRepository.save(rule);

        coupon = new Coupon();
        coupon.setCustomer(customer);
        couponRepository.save(coupon);

        CouponRule couponRule = new CouponRule();
        couponRule.setCoupon(coupon);
        couponRuleRepository.save(couponRule);
    }

    @Test
    void applyCoupons() {
        CustomerOrder order = new CustomerOrder();
        order.setProducts(Set.of(product));

        List<String> strings = couponService.applyCoupons(Set.of(coupon.getId()), order);
        strings.forEach(System.out::println);
    }
}
