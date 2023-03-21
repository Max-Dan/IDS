package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.client.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@IdClass(CustomerCard.CustomerCardCompositeId.class)
public class CustomerCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    @Id
    @ManyToOne
    @JoinColumn
    private Customer customer;
    @Id
    @ManyToOne
    @JoinColumn
    private Card card;
    private int points = 0;
    private int tier = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCard that = (CustomerCard) o;
        return id == that.id && customer.equals(that.customer) && card.equals(that.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, card);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerCardCompositeId implements Serializable {
        private long id;
        private Customer customer;
        private Card card;
    }
}
