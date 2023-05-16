package it.unicam.cs.ids.lp.client.card.programs;

import it.unicam.cs.ids.lp.client.card.CustomerCard;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class CashbackCard extends CustomerCard {

    private int remainingCashback;

    @Override
    public void applyBonus() {
        remainingCashback += 50;
    }

}

