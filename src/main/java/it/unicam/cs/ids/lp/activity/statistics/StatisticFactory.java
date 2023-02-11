package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.Activity;
import org.hibernate.cfg.NotYetImplementedException;

public class StatisticFactory {

    public static Statistic createStatistic(Activity activity, StatisticType type) {
        Statistic statistic = typeToStatisticDefault(type);
        statistic.setValue(statistic.calculate(activity));
        return statistic;
    }

    private static Statistic typeToStatisticDefault(StatisticType statisticType) {
        throw new NotYetImplementedException();
    }
}
