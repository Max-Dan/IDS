package it.unicam.cs.ids.lp.activity.statistics.card;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.statistics.Statistic;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardClientsStatistic implements Statistic<Card> {

    private final CustomerRepository customerRepository;

    public Double apply(Card card) {
        return (double) customerRepository.countByCards_Card(card);
    }
}
