package it.unicam.cs.ids.lp.rules.platform_rules.campaign;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.rules.platform_rules.AbstractPlatformRule;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CampaignRule extends AbstractPlatformRule {

    @ManyToOne
    @JoinColumn
    private Campaign campaign;

}
