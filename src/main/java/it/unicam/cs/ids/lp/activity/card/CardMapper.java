package it.unicam.cs.ids.lp.activity.card;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CardMapper implements Function<CardRequest, Card> {
    @Override
    public Card apply(CardRequest cardRequest) {
        Card card = new Card();
        card.setName(cardRequest.name());
        return card;
    }
}
