package it.unicam.cs.ids.lp.activity.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StatisticsCalculatorService {

    @Autowired
    private StatisticRepository statisticRepository;

    private Statistic typeToStatistic(StatisticType statisticType) {
        return null;
    }

    public void analyzeData(Collection<StatisticType> statisticTypes) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        statisticTypes
                .forEach(statisticType ->
                        executorService.execute(() -> {
                            Statistic statistic = typeToStatistic(statisticType);
                            statistic.setValue(statistic.calculate());
                            statisticRepository.save(statistic);
                        })
                );
    }
}
