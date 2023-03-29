package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.activity.card.CardProgram;
import it.unicam.cs.ids.lp.client.card.CustomerCardCompositeId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerCardUpdateRequest {
    private CustomerCardCompositeId customerCardId;
    private int points;
    private int tier;
    private int remainingCashback;
    private LocalDate membership;
    private boolean family;
    private CardProgram newProgram;
}


