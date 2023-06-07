package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.card.programs.ProgramDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerCardService {

    private final CustomerCardRepository customerCardRepository;
    private final CustomerCardMapper customerCardMapper;
    private final CustomerRepository customerRepository;
    private final ProgramDataService programDataService;


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
        programDataService.createNotExistentProgramData(customerCard.getCard().getReferralRules(), customerCard);
        customer.getCards().add(customerCard);
        customerRepository.save(customer);
        return customerCard;
    }

    public void deleteCustomerCard(long customerCardId) {
        customerCardRepository.deleteById(customerCardId);
    }
}
