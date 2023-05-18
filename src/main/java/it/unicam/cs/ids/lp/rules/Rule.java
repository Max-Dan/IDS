package it.unicam.cs.ids.lp.rules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.lp.client.card.programs.ProgramData;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.platform_rules.AbstractPlatformRule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Rule<R> {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private AbstractPlatformRule platformRule;

    public abstract R applyRule(CustomerOrder item);

    public abstract ProgramData createProgramData();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule<?> rule = (Rule<?>) o;
        return id == rule.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public abstract Integer seeBonus(CustomerOrder order);
}
