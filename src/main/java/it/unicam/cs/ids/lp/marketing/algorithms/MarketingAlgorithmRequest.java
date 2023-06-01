package it.unicam.cs.ids.lp.marketing.algorithms;

import java.util.Set;

public record MarketingAlgorithmRequest(
        long id,
        String algorithmName,
        String expirationDate, // Date format "yyyy-MM-dd"
        Set<String> deliveryDates, // Dates format "MM-dd"
        long messageModelId,
        Set<Long> subscribedCustomerIds
) {
}



