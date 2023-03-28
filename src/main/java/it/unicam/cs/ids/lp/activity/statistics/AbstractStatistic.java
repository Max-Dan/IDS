package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.Activity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@IdClass(StatisticId.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractStatistic<T> implements Statistic<T> {

    @Id
    @ManyToOne
    @JoinColumn
    private Activity activity;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatisticType type;

    @Id
    private LocalDate date;

    private double value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractStatistic<?> that = (AbstractStatistic<?>) o;
        return Objects.equals(activity, that.activity) && type == that.type && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, type, date);
    }
}
