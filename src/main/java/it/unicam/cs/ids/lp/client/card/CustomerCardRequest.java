package it.unicam.cs.ids.lp.client.card;

record CustomerCardRequest(long customerId,
                           CardProgram program,
                           boolean family) {
}
