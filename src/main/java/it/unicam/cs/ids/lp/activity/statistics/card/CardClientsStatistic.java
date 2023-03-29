package it.unicam.cs.ids.lp.activity.statistics.card;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardClientsStatistic extends CardStatistic {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Double apply(Card card) {
        return (double) customerRepository.countByCards_Card(card);
    }
}
