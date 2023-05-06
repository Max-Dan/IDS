package it.unicam.cs.ids.lp.activity.statistics.factory;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.statistics.Statistic;
import it.unicam.cs.ids.lp.activity.statistics.StatisticType;
import it.unicam.cs.ids.lp.activity.statistics.card.CardClientsStatistic;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CardStatisticFactory extends AbstractStatisticFactory<CardStatistic, Card> {

    private final CustomerRepository customerRepository;

    @Override
    public CardStatistic createStatistic(StatisticType type, Card card) {
        CardStatistic statistic = new CardStatistic();
        statistic.setType(type);
        statistic.setDate(LocalDate.now());
        statistic.setCard(card);
        statistic.setStatistic(getCardStatistic(type));
        return statistic;
    }

    /**
     * Restituisce le statistiche inerenti alla carta dell'attività
     *
     * @param type il tipo della statistica
     * @return la statistica
     */
    private Statistic<Card> getCardStatistic(StatisticType type) {
        return switch (type) {
            case CARD_CLIENTS -> new CardClientsStatistic(customerRepository);
        };
    }
}
