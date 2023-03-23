package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.ContentCategory;

import java.time.LocalDate;

public record CampaignRequest(String description,
                              String shopUrl,
                              ContentCategory category,
                              LocalDate end) {
}
