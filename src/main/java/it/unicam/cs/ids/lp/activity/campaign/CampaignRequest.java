package it.unicam.cs.ids.lp.activity.campaign;

import java.time.LocalDate;

public record CampaignRequest(String name,
                              LocalDate end) {
}
