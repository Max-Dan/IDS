package it.unicam.cs.ids.lp.activity.campaign.rules;

import it.unicam.cs.ids.lp.rules.platform_rules.campaign.CampaignRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractCampaignRuleRepository<T extends CampaignRule>
        extends JpaRepository<T, Long> {
}
