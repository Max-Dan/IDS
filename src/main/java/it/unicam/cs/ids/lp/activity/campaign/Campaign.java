package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.campaign.rules.AbstractRule;
import it.unicam.cs.ids.lp.activity.card.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
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
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    @ToString.Exclude
    private Card card;
    private LocalDate start;
    private LocalDate end;
}
