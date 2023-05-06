package it.unicam.cs.ids.lp.activity.statistics.card;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.statistics.StatisticType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Service
public class CardStatisticMapper implements BiFunction<StatisticType, Card, CardStatistic> {

    @Override
    public CardStatistic apply(StatisticType type, Card card) {
        CardStatistic cardStatistic = new CardStatistic();
        cardStatistic.setType(type);
        cardStatistic.setCard(card);
        cardStatistic.setDate(LocalDate.now());
        return cardStatistic;
    }
}
