package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatisticRepository;
import it.unicam.cs.ids.lp.activity.statistics.factory.CardStatisticFactory;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsCalculatorService implements StatisticAnalyzer<Card> {

    private final CustomerRepository customerRepository;
    private final CardStatisticRepository cardStatisticRepository;

    public StatisticsCalculatorService(CustomerRepository customerRepository, CardStatisticRepository cardStatisticRepository) {
        this.customerRepository = customerRepository;
        this.cardStatisticRepository = cardStatisticRepository;
    }

    @Override
    public List<CardStatistic> analyzeData(List<StatisticType> statisticTypes, Card card) {
        return statisticTypes.parallelStream()
                .map(type -> this.calculateAndSaveCardStatistic(type, card))
                .toList();
    }

    private CardStatistic calculateAndSaveCardStatistic(StatisticType type, Card card) {
        CardStatistic statistic = new CardStatisticFactory(customerRepository).applyStatistic(type, card);
        cardStatisticRepository.save(statistic);
        return statistic;
    }
}
