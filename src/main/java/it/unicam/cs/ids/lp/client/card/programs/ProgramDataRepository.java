package it.unicam.cs.ids.lp.client.card.programs;

import it.unicam.cs.ids.lp.rules.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramDataRepository
        extends JpaRepository<ProgramData, Long> {
    ProgramData findByRule(Rule<?> rule);

}
