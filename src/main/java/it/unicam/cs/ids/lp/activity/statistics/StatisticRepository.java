package it.unicam.cs.ids.lp.activity.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface StatisticRepository<T extends AbstractStatistic<?>>
        extends JpaRepository<T, Long> {
}
