package it.unicam.cs.ids.lp.rules;

import it.unicam.cs.ids.lp.client.card.CustomerCard;
import jakarta.persistence.Entity;

@Entity
public abstract class ReferralRule<T> extends Rule<T> {

    public abstract void applyReferral(CustomerCard customerCard);
}
