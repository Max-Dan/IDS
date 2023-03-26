package it.unicam.cs.ids.lp.activity.campaign.rules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractRuleRepository<E extends AbstractRule> extends JpaRepository<E, Long> {
}
