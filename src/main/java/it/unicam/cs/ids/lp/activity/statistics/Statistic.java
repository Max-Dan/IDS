package it.unicam.cs.ids.lp.activity.statistics;

import java.util.function.Function;

public interface Statistic<T> extends Function<T, Double> {
}
