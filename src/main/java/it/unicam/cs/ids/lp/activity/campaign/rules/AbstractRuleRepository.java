package it.unicam.cs.ids.lp.activity.campaign.rules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRuleRepository<T extends AbstractRule<?>>
        extends JpaRepository<T, Long> {
}
