package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;

import java.util.List;

/**
 * Interfaccia che analizza dati per fornire statistiche
 *
 * @param <I> tipo dell'oggetto su cui generare la statistica
 */
public interface StatisticAnalyzer<I> {

    List<CardStatistic> analyzeData(List<StatisticType> statisticTypes, I id);
}
