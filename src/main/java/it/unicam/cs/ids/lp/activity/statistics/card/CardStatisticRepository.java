package it.unicam.cs.ids.lp.activity.statistics.card;

import it.unicam.cs.ids.lp.activity.statistics.StatisticRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardStatisticRepository
        extends StatisticRepository<CardStatistic> {
}
