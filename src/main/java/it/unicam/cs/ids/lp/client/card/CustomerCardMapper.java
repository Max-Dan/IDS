package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CustomerCardMapper implements Function<CustomerCardRequest, CustomerCard> {

    private final CustomerRepository customerRepository;
    private final CardRepository cardRepository;
    private final CustomerCardRepository customerCardRepository;

    @Override
    public CustomerCard apply(CustomerCardRequest customerCardRequest) {
        CustomerCard customerCard;
        switch (customerCardRequest.program()) {
            case CASHBACK -> customerCard = new CashbackCard();
            case MEMBERSHIP -> customerCard = new MembershipCard();
            default -> throw new IllegalStateException("Invalid CardProgram");
        }
        customerCard.setCustomer(customerRepository.findById(customerCardRequest.customerId()).orElseThrow());
        customerCard.setCard(cardRepository.findById(customerCardRequest.cardId()).orElseThrow());
        customerCard.setFamily(customerCardRequest.family());
        customerCard.setReferralCode(customerCardRequest.customerId() + "-" + customerCardRequest.cardId());
        return customerCard;
    }
}



