package it.unicam.cs.ids.lp.client.card;

public record CustomerCardRequest(long customerId,
                                  long cardId,
                                  CardProgram program,
                                  boolean family,
                                  String referredCode) {
}

