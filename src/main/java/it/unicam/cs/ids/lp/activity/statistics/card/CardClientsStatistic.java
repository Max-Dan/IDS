package it.unicam.cs.ids.lp.activity.statistics.card;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.statistics.Statistic;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CardClientsStatistic implements Statistic<Card> {

    private final CustomerRepository customerRepository;

    public CardClientsStatistic(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Double apply(Card card) {
        return (double) customerRepository.countByCards_Card(card);
    }
}
