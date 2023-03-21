package it.unicam.cs.ids.lp.client.card.activitycard;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class CustomerActivityCard extends CustomerCard {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_activity_card_id")
    private List<Card> activityCards;
    private String referred;
    private String referralCode;
    private int referredTo;
    private CACardProgram program;
    private boolean family = false;
    private String activityName;

}