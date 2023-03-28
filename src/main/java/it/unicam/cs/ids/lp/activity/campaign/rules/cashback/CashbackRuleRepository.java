package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.rules.AbstractRuleRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CashbackRuleRepository extends AbstractRuleRepository<CashbackRule> {
}
