package it.unicam.cs.ids.lp.rules.platform_rules.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRuleRepository extends JpaRepository<CampaignRule, Long> {
    List<CampaignRule> findByCampaign_Id(long id);

}
