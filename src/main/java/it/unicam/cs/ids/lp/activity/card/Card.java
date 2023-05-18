package it.unicam.cs.ids.lp.activity.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.rules.ReferralRule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Activity> activities = new LinkedList<>();

    @OneToMany
    @ToString.Exclude
    @JsonIgnore
    private List<ReferralRule<?>> referralRules = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id.equals(card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
