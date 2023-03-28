package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsCalculatorService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private StatisticMapper statisticMapper;

    public List<String> analyzeData(List<StatisticType> statisticTypes, long acrivityId) {
        Activity activity = activityRepository.findById(acrivityId).orElseThrow();
        return statisticTypes.parallelStream()
                .map(statisticType -> calculateAndSaveStatistic(activity, statisticType))
                .toList();
    }

    private String calculateAndSaveStatistic(Activity activity, StatisticType statisticType) {
        AbstractStatistic<?> statistic = statisticMapper.apply(statisticType, activity);
        statisticRepository.save(statistic);
        return "" + statistic.getClass().getSimpleName() + "    " + statistic.getValue();
    }
}
