package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatisticMapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsCalculatorService implements StatisticAnalyzer<Card> {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private CardStatisticMapper cardStatisticMapper;

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public List<String> analyzeData(List<StatisticType> statisticTypes, Card card) {
        return statisticTypes.parallelStream()
                .map(type -> this.calculateAndSaveCardStatistic(type, card))
                .toList();
    }

    private String calculateAndSaveCardStatistic(StatisticType type, Card card) {
        CardStatistic statistic = cardStatisticMapper.apply(type, card);
        //crea il bean per usare la dependency injection
        CardStatistic cardStatisticBean = (CardStatistic) beanFactory.getBean(
                statisticMapper.getTypeClass(statistic.getType())
                        .getClass());
        double result = cardStatisticBean.apply(card);
        statistic.setValue(result);
        statisticRepository.save(statistic);
        return "" + statistic.getClass().getSimpleName() + "    " + statistic.getValue();
    }
}
