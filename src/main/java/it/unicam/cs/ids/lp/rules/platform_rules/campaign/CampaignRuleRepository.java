package it.unicam.cs.ids.lp.rules.platform_rules.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRuleRepository extends JpaRepository<CampaignRule, Long> {
}
