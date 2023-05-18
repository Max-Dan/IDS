package it.unicam.cs.ids.lp.client.card;

public record CustomerCardRequest(long customerId,
                                  long cardId,
                                  boolean family,
                                  String referredCode) {
}

