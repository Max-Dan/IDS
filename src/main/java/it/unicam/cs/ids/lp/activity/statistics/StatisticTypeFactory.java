package it.unicam.cs.ids.lp.activity.statistics;

import org.hibernate.cfg.NotYetImplementedException;

import java.util.List;

public class StatisticTypeFactory {

    public static List<StatisticType> getActivityTypeStatistics() {
        throw new NotYetImplementedException();
    }

    public static List<StatisticType> getCampaignTypeStatistics() {
        throw new NotYetImplementedException();
    }

    public static List<StatisticType> getCardTypeStatistics() {
        return List.of(StatisticType.CARD_CLIENTS);
    }
}
