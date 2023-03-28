package it.unicam.cs.ids.lp.activity.statistics;

import org.hibernate.cfg.NotYetImplementedException;

import java.util.List;

public class StatisticFactory {

    public static List<StatisticType> getActivityStatistics() {
        throw new NotYetImplementedException();
    }

    public static List<StatisticType> getCampaignStatistics() {
        throw new NotYetImplementedException();
    }

    public static List<StatisticType> getCardStatistics() {
        return List.of(StatisticType.CARD_CLIENTS);
    }
}
