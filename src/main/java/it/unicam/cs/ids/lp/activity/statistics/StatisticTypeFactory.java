package it.unicam.cs.ids.lp.activity.statistics;

import org.hibernate.cfg.NotYetImplementedException;

import java.util.List;

/**
 * Factory usata per ottenere una lista di statistiche per categoria
 */
public class StatisticTypeFactory {

    /**
     * Restituisce le statistiche sulle attività
     *
     * @return le statistiche sulle attività
     */
    public static List<StatisticType> getActivityTypeStatistics() {
        throw new NotYetImplementedException();
    }

    /**
     * Restituisce le statistiche sulle campagne
     *
     * @return le statistiche sulle campagne
     */
    public static List<StatisticType> getCampaignTypeStatistics() {
        throw new NotYetImplementedException();
    }

    /**
     * Restituisce le statistiche sulle carte delle attività
     *
     * @return le statistiche sulle carte delle attività
     */
    public static List<StatisticType> getCardTypeStatistics() {
        return List.of(StatisticType.CARD_CLIENTS);
    }
}
