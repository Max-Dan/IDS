package it.unicam.cs.ids.lp.activity.campaign;

import java.time.LocalDate;
import java.util.Set;

public record CampaignRequest(String name,
                              LocalDate end,
                              Set<RulesEnum> rules) {
}
