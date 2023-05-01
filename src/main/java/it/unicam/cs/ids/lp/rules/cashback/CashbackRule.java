package it.unicam.cs.ids.lp.rules.cashback;

import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.Rule;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CashbackRule extends Rule<Integer> {

    /**
     * prodotti soggetti al cashback
     */
    @OneToMany(orphanRemoval = true)
    @JoinColumn
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();

    /**
     * percentuale di cashback per i prodotti
     */
    private float cashbackRate;

    @Override
    public Integer applyRule(CustomerOrder order) {
        return order.getProducts()
                .parallelStream()
                .filter(product -> this.getProducts()
                        .contains(product))
                .map(product -> (int) (product.getPrice() / 100 * this.getCashbackRate()))
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashbackRule)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
