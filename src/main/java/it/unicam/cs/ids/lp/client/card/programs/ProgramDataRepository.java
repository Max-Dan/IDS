package it.unicam.cs.ids.lp.client.card.programs;

import it.unicam.cs.ids.lp.rules.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProgramDataRepository
        extends JpaRepository<ProgramData, Long> {
    Set<ProgramData> findByRule(Rule<?> rule);
}
