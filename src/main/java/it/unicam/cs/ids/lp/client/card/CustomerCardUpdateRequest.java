package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.rules.Rule;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerCardUpdateRequest {
    private long customerCardId;
    private int points;
    private int tier;
    private int remainingCashback;
    private LocalDate membership;
    private Rule<?> programRule;
    private boolean family;
}



