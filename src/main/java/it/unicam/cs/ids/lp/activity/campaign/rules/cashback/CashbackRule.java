package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Rule;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CashbackRule implements Rule<CustomerOrder, Integer> {

    @Id
    @GeneratedValue
    private long id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CashbackRule that = (CashbackRule) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
