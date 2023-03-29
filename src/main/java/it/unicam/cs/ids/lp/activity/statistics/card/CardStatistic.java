package it.unicam.cs.ids.lp.activity.statistics.card;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.statistics.AbstractStatistic;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Entity
@Service
@Getter
@Setter
@ToString
@RequiredArgsConstructor
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CardStatistic extends AbstractStatistic<Card> {

    @OneToOne(orphanRemoval = true)
    @JoinColumn
    private Card card;

    @Transient
    @Autowired
    private CardStatisticMapper statisticMapper;

    @Transient
    @Autowired
    private CardStatisticRepository cardStatisticRepository;

    @Transient
    @Autowired
    private BeanFactory beanFactory;

    @Override
    public Double apply(Card card) {
        throw new RuntimeException();
    }
}
