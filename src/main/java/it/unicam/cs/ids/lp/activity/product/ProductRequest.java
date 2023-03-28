package it.unicam.cs.ids.lp.activity.product;

import java.util.List;

public record ProductRequest(String name,
                             List<Long> activitiesIds,
                             int price
) {
}
