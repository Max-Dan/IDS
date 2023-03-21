package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.CardProgram;

public record CustomerCardRequest(long customerId,
                                  long cardId,
                                  CardProgram program,
                                  boolean family,
                                  String referredCode) {
}

