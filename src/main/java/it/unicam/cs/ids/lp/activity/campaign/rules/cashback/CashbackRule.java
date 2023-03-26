package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Rule;
import it.unicam.cs.ids.lp.activity.campaign.rules.AbstractRule;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CashbackRule extends AbstractRule implements Rule<Integer> {

    /**
     * prodotti soggetti al cashback
     */
    @OneToMany(orphanRemoval = true)
    @JoinColumn
    @ToString.Exclude
    private Set<Product> products;
    /**
     * percentuale di cashback per i prodotti
     */
    private float cashbackRate;

    @Override
    public Integer apply(CustomerOrder order) {
        return order.getProducts()
                .parallelStream()
                .filter(product -> this.getProducts()
                        .contains(product))
                .map(product -> (int) (product.getPrice() / 100 * this.getCashbackRate()))
                .reduce(Integer::sum)
                .orElse(0);
    }
}
