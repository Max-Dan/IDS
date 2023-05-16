package it.unicam.cs.ids.lp.rules.platform_rules;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractPlatformRuleRepository<T extends AbstractPlatformRule>
        extends JpaRepository<T, Long> {
}
