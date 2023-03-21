package it.unicam.cs.ids.lp.client.card.activitycard;

import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard.CustomerCardCompositeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CACardMapper implements Function<CACardRequest, CustomerActivityCard> {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardRepository CardRepository;

    @Override
    public CustomerActivityCard apply(CACardRequest caCardRequest) {
        CustomerActivityCard customerActivityCard = new CustomerActivityCard();

        CustomerCardCompositeId customerCardCompositeId = caCardRequest.customerCardCompositeId();
        customerActivityCard.setCustomer(customerRepository.findById(customerCardCompositeId.getCustomer().getId()).orElseThrow());
        customerActivityCard.setCard(CardRepository.findById(customerCardCompositeId.getCard().getId()).orElseThrow());
        customerActivityCard.setProgram(caCardRequest.program());
        customerActivityCard.setFamily(caCardRequest.family());
        customerActivityCard.setReferred(caCardRequest.referredCode());
        customerActivityCard.setActivityName(caCardRequest.activityName());

        return customerActivityCard;
    }
}

