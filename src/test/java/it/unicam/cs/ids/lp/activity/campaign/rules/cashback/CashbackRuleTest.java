package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.ContentCategory;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.activity.product.ProductMapper;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.activity.product.ProductRequest;
import it.unicam.cs.ids.lp.activity.registration.ActivityRegistrationController;
import it.unicam.cs.ids.lp.activity.registration.ActivityRequest;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
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

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CashbackRuleTest {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ActivityRegistrationController activityRegistrationController;
    private CashbackRule cashbackRule;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ProductRepository productRepository;


    @BeforeAll
    public void setUp() {
        cashbackRule = new CashbackRule();
        // mi assicuro che ci sia almeno una attività
        Activity activity = activityRepository.findByName("acqua e sapone");
        if (activity == null)
            activityRegistrationController.registerActivity(new ActivityRequest(
                            "acqua e sapone",
                            "fd",
                            "222-222-2222",
                            "fdsa@daf.com",
                            ContentCategory.BEAUTY,
                            "password"
                    )
            );

        Product p1 = productRepository.findByName("Sapone");
        if (p1 == null)
            p1 = productRepository.save(productMapper.apply(new ProductRequest(
                            "Sapone",
                            List.of(1L),
                            200
                    )
            ));

        Product p2 = productRepository.findByName("Mascara");
        if (p2 == null)
            p2 = productRepository.save(productMapper.apply(new ProductRequest(
                            "Mascara",
                            List.of(1L),
                            600
                    )
            ));
        cashbackRule.setProducts(Set.of(p1, p2));
    }

    @Test
    void applyTest() {
        cashbackRule.setCashbackRate(5);
        CustomerOrder order = new CustomerOrder();
        order.setProducts(cashbackRule.getProducts());
        int cashback = cashbackRule.apply(order);
        Assertions.assertEquals(200 / 100 * 5 + 600 / 100 * 5, cashback);
    }
}
