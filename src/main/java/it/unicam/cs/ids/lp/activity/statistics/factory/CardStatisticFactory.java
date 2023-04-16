package it.unicam.cs.ids.lp.activity.statistics.factory;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.statistics.Statistic;
import it.unicam.cs.ids.lp.activity.statistics.StatisticType;
import it.unicam.cs.ids.lp.activity.statistics.card.CardClientsStatistic;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;
import it.unicam.cs.ids.lp.client.CustomerRepository;

import java.time.LocalDate;

public class CardStatisticFactory extends AbstractStatisticFactory<CardStatistic, Card> {

    private final CustomerRepository customerRepository;

    public CardStatisticFactory(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CardStatistic createStatistic(StatisticType type, Card card) {
        CardStatistic statistic = new CardStatistic();
        statistic.setType(type);
        statistic.setDate(LocalDate.now());
        statistic.setCard(card);
        statistic.setStatistic(getCardStatistic(type));
        return statistic;
    }

    private Statistic<Card> getCardStatistic(StatisticType type) {
        return switch (type) {
            case CARD_CLIENTS -> new CardClientsStatistic(customerRepository);
        };
    }
}
