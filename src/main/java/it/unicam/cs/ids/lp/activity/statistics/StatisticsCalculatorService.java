package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StatisticsCalculatorService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public Collection<Statistic> analyzeData(Collection<StatisticType> statisticTypes, String activityNameId) {
        Activity activity = activityRepository.findById(activityNameId).orElseThrow();
        ExecutorService executorService = Executors.newCachedThreadPool();
        return statisticTypes.stream()
                .map(statisticType -> getStatistic(activity, executorService, statisticType))
                .toList();
    }

    private Statistic getStatistic(Activity activity, ExecutorService executorService, StatisticType statisticType) {
        try {
            return executorService.submit(() -> {
                Statistic statistic = StatisticFactory.createStatistic(activity, statisticType);
                statisticRepository.save(statistic);
                return statistic;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
