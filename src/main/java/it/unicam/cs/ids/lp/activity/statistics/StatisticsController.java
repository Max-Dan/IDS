package it.unicam.cs.ids.lp.activity.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{activityNameId}/stats")
    public ResponseEntity<?> getStatistics(@PathVariable String activityNameId, @RequestBody List<StatisticType> statisticTypes) {
        return new ResponseEntity<>(
                statisticsCalculatorService.analyzeData(statisticTypes, activityNameId),
                HttpStatus.OK
        );
    }
}
