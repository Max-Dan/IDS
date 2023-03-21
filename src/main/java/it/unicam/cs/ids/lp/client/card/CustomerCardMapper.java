package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerCardMapper implements Function<CustomerCardRequest, CustomerCard> {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public CustomerCard apply(CustomerCardRequest customerCardRequest) {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setCustomer(customerRepository.findById(customerCardRequest.customerId()).orElseThrow());
        customerCard.setCard(cardRepository.findById(customerCardRequest.cardId()).orElseThrow());
        customerCard.setProgram(customerCardRequest.program());
        customerCard.setFamily(customerCardRequest.family());
        customerCard.setReferred(customerCardRequest.referredCode());

        return customerCard;
    }
}

