package it.unicam.cs.ids.lp.activity.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository
        extends JpaRepository<AbstractStatistic<?>, StatisticId> {
}
