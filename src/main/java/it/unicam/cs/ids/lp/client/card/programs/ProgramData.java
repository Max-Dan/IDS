package it.unicam.cs.ids.lp.client.card.programs;

import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.rules.Rule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ProgramData {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private Rule<?> rule;

    @ManyToOne
    @JoinColumn(name = "customer_card_id")
    private CustomerCard customerCard;
}
