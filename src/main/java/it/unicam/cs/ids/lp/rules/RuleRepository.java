package it.unicam.cs.ids.lp.rules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository<T extends Rule<?>>
        extends JpaRepository<T, Long> {

}
