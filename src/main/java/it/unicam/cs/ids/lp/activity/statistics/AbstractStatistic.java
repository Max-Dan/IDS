package it.unicam.cs.ids.lp.activity.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractStatistic<T> {

    @Id
    @GeneratedValue
    private long id;

    private StatisticType type;

    private LocalDate date;

    private double value;

    @Transient
    @JsonIgnore
    private Statistic<T> statistic;

    public final double getResult(T item) {
        Objects.requireNonNull(statistic);
        return this.statistic.apply(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractStatistic<?> that = (AbstractStatistic<?>) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
