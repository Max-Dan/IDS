package it.unicam.cs.ids.lp.activity.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activity/{activityId}")
public class StatisticsController {

    @Autowired
    private StatisticsCalculatorService statisticsCalculatorService;

    @GetMapping("/cardStats")
    public ResponseEntity<List<String>> getCardStatistics(@PathVariable long activityId) {
        return ResponseEntity.ok(
                statisticsCalculatorService.analyzeCardData(StatisticTypeFactory.getCardTypeStatistics(), activityId)
        );
    }

//    @GetMapping("/{activityId}/stats")
//    public ResponseEntity<List<String>> getActivityStatistics(@PathVariable long activityId) {
//        return ResponseEntity.ok(
//                statisticsCalculatorService.analyzeActivityData(StatisticTypeFactory.getActivityTypeStatistics(), activityId)
//        );
//    }
//
//    @GetMapping("/{activityId}/personalStats")
//    public ResponseEntity<List<String>> getPersonalStatistics(@PathVariable long activityId, @RequestBody List<StatisticType> statisticTypes) {
//        return ResponseEntity.ok(
//                statisticsCalculatorService.analyzeActivityData(statisticTypes, activityId)
//        );
//    }
//
//    @GetMapping("/{activityId}/campaign/{campaignId}/stats")
//    public ResponseEntity<List<String>> getCardStatistics(@PathVariable long activityId, @PathVariable long campaignId) {
//        return ResponseEntity.ok(
//                statisticsCalculatorService.analyzeActivityData(StatisticTypeFactory.getCampaignTypeStatistics(), activityId)
//        );
//    }
}
