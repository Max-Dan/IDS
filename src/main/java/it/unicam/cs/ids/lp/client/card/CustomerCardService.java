package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.card.programs.ProgramDataMapper;
import it.unicam.cs.ids.lp.client.card.programs.ProgramDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerCardService {

    private final CustomerCardRepository customerCardRepository;
    private final CustomerCardMapper customerCardMapper;
    private final ProgramDataRepository programDataRepository;
    private final ProgramDataMapper programDataMapper;
    private final CustomerRepository customerRepository;


    public CustomerCard createCustomerCard(CustomerCardRequest request) {
        Customer customer = customerRepository.findById(request.customerId()).orElseThrow();
        if (customer.getCards().stream().anyMatch(customerCard -> customerCard.getCard().getId() == request.cardId()))
            return null;
        CustomerCard customerCard = customerCardMapper.apply(request);
        if (request.referredCode() == null
                || customerCardRepository.findByReferralCode(request.referredCode()).isEmpty()) {
            customerCardRepository.save(customerCard);
            customer.getCards().add(customerCard);
            customerRepository.save(customer);
            return customerCard;
        }

        customerCardRepository.save(customerCard);
        customerCard.getCard().getReferralRules()
                .stream()
                .map(referralRule -> programDataMapper.map(referralRule, customerCard))
                .map(programDataRepository::save)
                .forEach(programData -> customerCard.getProgramsData().add(programData));
        customerCardRepository.save(customerCard);
        customer.getCards().add(customerCard);
        customerRepository.save(customer);
        return customerCard;
    }

    public void deleteCustomerCard(long customerCardId) {
        customerCardRepository.deleteById(customerCardId);
    }
}
