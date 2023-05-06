package it.unicam.cs.ids.lp.activity.campaign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.lp.activity.card.Card;
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
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    @JsonIgnore
    private Card card;

    private LocalDate startDate;

    private LocalDate endDate;

    public boolean isCurrentlyActive() {
        return startDate.isBefore(LocalDate.now())
                && endDate.isAfter(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return id == campaign.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
