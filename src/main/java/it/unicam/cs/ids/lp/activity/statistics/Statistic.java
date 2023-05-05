package it.unicam.cs.ids.lp.activity.statistics;

import java.util.function.Function;

/**
 * Interfaccia che rappresenta una statistica
 *
 * @param <T> il tipo di parametro che ha bisogno la statistica
 */
public interface Statistic<T> extends Function<T, Double> {
}
