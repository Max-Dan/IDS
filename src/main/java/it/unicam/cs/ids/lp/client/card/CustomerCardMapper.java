package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.card.programs.CashbackCard;
import it.unicam.cs.ids.lp.client.card.programs.MembershipCard;
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
        customerCard.setProgram(customerCardRequest.program());
        customerCard.setFamily(customerCardRequest.family());
        customerCard.setReferralCode(customerCardRequest.customerId() + "-" + customerCardRequest.cardId() + "-" + customerCardRequest.program());
        String referralCode = customerCardRequest.referredCode();
        if (referralCode != null) {
            CustomerCard referredBy = customerCardRepository.findByReferralCode(referralCode)
                    .orElseThrow(() -> new IllegalStateException("Invalid referral code"));
            customerCard.setReferredBy(referredBy);
            customerCard.applyBonus();
        }
        return customerCard;
    }
}



