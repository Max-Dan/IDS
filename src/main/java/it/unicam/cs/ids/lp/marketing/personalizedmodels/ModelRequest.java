package it.unicam.cs.ids.lp.marketing.personalizedmodels;

public record ModelRequest(long couponId,
                           String modelName,
                           String messageText) {
}
