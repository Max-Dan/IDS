package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.ContentCategory;

public record CampaignRequest(String description,
                              String shopUrl,
                              ContentCategory category) {
}
