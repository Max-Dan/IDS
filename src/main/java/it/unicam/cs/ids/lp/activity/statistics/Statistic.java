package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.Activity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@IdClass(Statistic.StatisticId.class)
public abstract class Statistic {

    @Id
    @ManyToOne
    private Activity activity;
    @Id
    private StatisticType type;
    @Id
    private LocalDate date;
    private double value;

    public Statistic(Activity activity, StatisticType category) {
        this.activity = activity;
        this.type = category;
        this.date = LocalDate.now();
    }

    abstract double calculate();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Statistic statistic = (Statistic) o;
        return activity != null && Objects.equals(activity, statistic.activity);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static class StatisticId implements Serializable {
        private Activity activity;
        private Activity.ContentCategory category;
        private LocalDate date;
    }
}

