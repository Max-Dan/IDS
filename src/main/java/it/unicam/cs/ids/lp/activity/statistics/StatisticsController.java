package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activity/{activityId}")
public class StatisticsController {

    private final StatisticsCalculatorService statisticsCalculatorService;

    private final CardRepository cardRepository;

    public StatisticsController(StatisticsCalculatorService statisticsCalculatorService,
                                CardRepository cardRepository) {
        this.statisticsCalculatorService = statisticsCalculatorService;
        this.cardRepository = cardRepository;
    }

    @GetMapping("/cardStats")
    public ResponseEntity<List<CardStatistic>> getCardStatistics(@PathVariable long activityId) {
        return ResponseEntity.ok(
                statisticsCalculatorService.analyzeData(StatisticTypeFactory.getCardTypeStatistics(),
                        cardRepository.findByActivities_Id(activityId).orElseThrow())
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
