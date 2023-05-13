package it.unicam.cs.ids.lp.client.card.programs;

import it.unicam.cs.ids.lp.client.card.CustomerCard;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class MembershipCard extends CustomerCard {

    private LocalDate membership;

    public void extendMembership(int weeks) {
        membership = Objects.requireNonNullElseGet(membership, LocalDate::now)
                .plusWeeks(weeks);
    }
}
