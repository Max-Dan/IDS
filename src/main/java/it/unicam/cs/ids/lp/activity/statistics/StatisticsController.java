package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsCalculatorService statisticsCalculatorService;

    @GetMapping("/{activityId}/computeStats")
    public ResponseEntity<List<CardStatistic>> computeStatistics(@PathVariable long activityId,
                                                                 @RequestBody List<StatisticType> statistics) {
        return ResponseEntity.ok(
                statisticsCalculatorService.analyzeData(statistics, activityId)
        );
    }

    @GetMapping("/{activityId}/computeCardStats")
    public ResponseEntity<List<CardStatistic>> computeCardStatistics(@PathVariable long activityId) {
        return computeStatistics(activityId, StatisticTypeFactory.getCardTypeStatistics());
    }

    @GetMapping("/statisticTypes")
    public ResponseEntity<List<StatisticType>> getStatisticTypes() {
        return ResponseEntity.ok(List.of(StatisticType.class.getEnumConstants()));
    }

    @GetMapping("/cardStats")
    public ResponseEntity<List<StatisticType>> getCardStats() {
        return ResponseEntity.ok(StatisticTypeFactory.getCardTypeStatistics());
    }
}
