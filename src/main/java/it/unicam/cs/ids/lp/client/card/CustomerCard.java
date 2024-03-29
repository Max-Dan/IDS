package it.unicam.cs.ids.lp.client.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.card.programs.ProgramData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomerCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Card card;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Campaign> campaigns = new LinkedList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn
    @ToString.Exclude
    @JsonIgnore
    private List<ProgramData> programsData = new LinkedList<>();

    private String referralCode;

    private boolean family = false;

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
