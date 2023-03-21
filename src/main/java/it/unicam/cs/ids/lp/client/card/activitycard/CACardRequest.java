package it.unicam.cs.ids.lp.client.card.activitycard;

import it.unicam.cs.ids.lp.client.card.CustomerCard.CustomerCardCompositeId;

public record CACardRequest(CustomerCardCompositeId customerCardCompositeId,
                            CACardProgram program,
                            boolean family,
                            String referredCode,
                            String activityName) {
}

