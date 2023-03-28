package it.unicam.cs.ids.lp.activity.campaign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.lp.activity.campaign.rules.AbstractRule;
import it.unicam.cs.ids.lp.activity.card.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


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
    @OneToMany(targetEntity = AbstractRule.class)
    @JoinColumn
    @ToString.Exclude
    private Set<Rule<?>> rules;
    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    @JsonIgnore
    private Card card;

    private LocalDate start;
    private LocalDate end;

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
