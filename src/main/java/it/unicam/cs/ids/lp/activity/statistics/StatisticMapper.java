package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.statistics.card.CardClientsStatistic;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

@Service
public class StatisticMapper implements Function<StatisticType, AbstractStatistic<?>> {

    @Override
    public AbstractStatistic<?> apply(StatisticType type) {
        AbstractStatistic<?> statistic = getTypeClass(type);
        statistic.setType(type);
        statistic.setDate(LocalDate.now());
        return statistic;
    }

    public AbstractStatistic<?> getTypeClass(StatisticType type) {
        return switch (type) {
            case CARD_CLIENTS -> new CardClientsStatistic();
        };
    }
}
