package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity/{activityId}")
public class StatisticsController {

    private final StatisticsCalculatorService statisticsCalculatorService;

    private final CardRepository cardRepository;


    @GetMapping("/cardStats")
    public ResponseEntity<List<CardStatistic>> getCardStatistics(@PathVariable long activityId) {
        return ResponseEntity.ok(
                statisticsCalculatorService.analyzeData(StatisticTypeFactory.getCardTypeStatistics(),
                        cardRepository.findByActivities_Id(activityId).orElseThrow())
        );
    }
}
