package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.Activity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Service
public class StatisticMapper implements BiFunction<StatisticType, Activity, AbstractStatistic<?>> {

    @Override
    public AbstractStatistic<?> apply(StatisticType type, Activity activity) {
        AbstractStatistic<?> statistic = getTypeClass(type);
        statistic.setType(type);
        statistic.setDate(LocalDate.now());
        statistic.setActivity(activity);
        return statistic;
    }

    private AbstractStatistic<?> getTypeClass(StatisticType type) {
//        return switch (type) {
//            case NUOVI_CLIENTI -> new
//        };
        return null;
    }
}
