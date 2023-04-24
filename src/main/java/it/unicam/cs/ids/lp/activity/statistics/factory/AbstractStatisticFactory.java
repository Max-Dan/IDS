package it.unicam.cs.ids.lp.activity.statistics.factory;

import it.unicam.cs.ids.lp.activity.statistics.AbstractStatistic;
import it.unicam.cs.ids.lp.activity.statistics.StatisticType;

import java.time.LocalDate;

/**
 * Factory per creare diversi tipi di statistiche
 *
 * @param <P> tipo del parametro da passare alla statistica
 * @param <E> tipo della statistica
 */
public abstract class AbstractStatisticFactory<E extends AbstractStatistic<P>, P> {

    abstract public E createStatistic(StatisticType type, P item);

    public E applyStatistic(StatisticType type, P item) {
        E statistic = createStatistic(type, item);
        statistic.setValue(statistic.getResult(item));
        statistic.setType(type);
        statistic.setDate(LocalDate.now());
        return statistic;
    }
}
