package it.unicam.cs.ids.lp.activity.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/activity")
public class StatisticsController {

    @Autowired
    private StatisticsCalculatorService statisticsCalculatorService;

    @GetMapping("/{activityId}/personalStats")
    public ResponseEntity<List<String>> getPersonalStatistics(@PathVariable long activityId, @RequestBody List<StatisticType> statisticTypes) {
        return ResponseEntity.ok(
                statisticsCalculatorService.analyzeData(statisticTypes, activityId)
        );
    }

    @GetMapping("/{activityId}/stats")
    public ResponseEntity<List<String>> getActivityStatistics(@PathVariable long activityId) {
        return ResponseEntity.ok(
                statisticsCalculatorService.analyzeData(StatisticFactory.getActivityStatistics(), activityId)
        );
    }

    @GetMapping("/{activityId}/cardStats")
    public ResponseEntity<List<String>> getStatistics(@PathVariable long activityId) {
        return ResponseEntity.ok(
                statisticsCalculatorService.analyzeData(StatisticFactory.getCardStatistics(), activityId)
        );
    }

    @GetMapping("/{activityId}/campaign/{campaignId}/stats")
    public ResponseEntity<List<String>> getStatistics(@PathVariable long activityId, @PathVariable long campaignId) {
        return ResponseEntity.ok(
                statisticsCalculatorService.analyzeData(StatisticFactory.getCampaignStatistics(), activityId)
        );
    }
}
