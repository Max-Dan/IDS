package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardProgram;
import it.unicam.cs.ids.lp.client.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@IdClass(CustomerCardCompositeId.class)
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

    @ManyToMany
    @ToString.Exclude
    private List<Campaign> campaigns;

    private int points = 0;

    private int tier = 1;

    private int remainingCashback;

    private LocalDate membership;

    private String referred;

    private String referralCode;

    private CardProgram program;

    private boolean family = false;

    public void extendMembership(int weeks) {
        membership = Objects.requireNonNullElseGet(membership, LocalDate::now)
                .plusWeeks(weeks);
    }

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

}
