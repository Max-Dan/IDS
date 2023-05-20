package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
class CashbackRuleTest {
    private CashbackRule cashbackRule;

    @BeforeEach
    public void setUp() {
        cashbackRule = new CashbackRule();
    }

    @Test
    void seeBonusTest() {
        cashbackRule.setCashbackRate(5);
        CustomerOrder order = new CustomerOrder();
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(200);
        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(600);
        order.setProducts(Set.of(p1, p2));
        cashbackRule.setProducts(Set.of(p1, p2));
        int cashback = cashbackRule.seeBonus(order);
        Assertions.assertEquals(200 / 100 * 5 + 600 / 100 * 5, cashback);
    }

    @Test
    void seeBonusTestProductNotCounted() {
        CustomerOrder order = new CustomerOrder();
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(200);
        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(600);
        Product p3 = new Product();
        p3.setId(3L);
        p3.setPrice(1000);
        order.setProducts(Set.of(p1, p2, p3));
        cashbackRule.setCashbackRate(5);
        cashbackRule.setProducts(Set.of(p1, p2));
        int cashback = cashbackRule.seeBonus(order);
        Assertions.assertEquals(200 / 100 * 5 + 600 / 100 * 5, cashback);
    }
}
