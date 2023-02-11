package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.Activity;
import org.hibernate.cfg.NotYetImplementedException;

public class StatisticFactory {

    /**
     * Crea la statistica
     *
     * @param activity l'attività su cui fare la statistica
     * @param type     il tipo di statistica
     * @return la statistica calcolata
     */
    public static Statistic createStatistic(Activity activity, StatisticType type) {
        Statistic statistic = typeToStatisticDefault(type);
        statistic.setValue(statistic.calculate(activity));
        return statistic;
    }

    /**
     * Converte il tipo a oggetto
     *
     * @param statisticType il tipo di statistica
     * @return la statistica
     */
    private static Statistic typeToStatisticDefault(StatisticType statisticType) {
        throw new NotYetImplementedException();
    }
}
