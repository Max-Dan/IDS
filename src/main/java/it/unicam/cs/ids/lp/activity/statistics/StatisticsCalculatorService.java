package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatisticRepository;
import it.unicam.cs.ids.lp.activity.statistics.factory.CardStatisticFactory;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsCalculatorService implements StatisticAnalyzer<Card> {

    private final CustomerRepository customerRepository;
    private final CardStatisticRepository cardStatisticRepository;
    private final CardRepository cardRepository;


    public List<CardStatistic> analyzeData(List<StatisticType> statisticTypes, long activityId) {
        return analyzeData(statisticTypes, cardRepository.findByActivities_Id(activityId).orElseThrow());
    }

    @Override
    public List<CardStatistic> analyzeData(List<StatisticType> statisticTypes, Card card) {
        return statisticTypes.parallelStream()
                .map(type -> this.calculateAndSaveCardStatistic(type, card))
                .toList();
    }

    /**
     * Calcola e salva nel database la statistica
     *
     * @param type il tipo di statistica
     * @param card la card per applicare la statistica
     * @return la statistica calcolata
     */
    private CardStatistic calculateAndSaveCardStatistic(StatisticType type, Card card) {
        CardStatistic statistic = new CardStatisticFactory(customerRepository).applyStatistic(type, card);
        cardStatisticRepository.save(statistic);
        return statistic;
    }
}
