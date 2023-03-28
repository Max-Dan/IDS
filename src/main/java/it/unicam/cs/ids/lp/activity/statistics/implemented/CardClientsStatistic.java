package it.unicam.cs.ids.lp.activity.statistics.implemented;

import it.unicam.cs.ids.lp.activity.statistics.AbstractStatistic;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardClientsStatistic extends AbstractStatistic<Long> {

    @Autowired
    private CustomerCardRepository customerCardRepository;

    @Override
    public Double apply(Long cardId) {
        return (double) customerCardRepository.countByCard_Id(cardId);
    }
}
