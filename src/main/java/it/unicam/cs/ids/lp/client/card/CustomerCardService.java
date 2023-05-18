package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.client.card.programs.ProgramData;
import it.unicam.cs.ids.lp.client.card.programs.ProgramDataRepository;
import it.unicam.cs.ids.lp.rules.Rule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerCardService {

    private final CustomerCardRepository customerCardRepository;
    private final CustomerCardMapper customerCardMapper;
    private final ProgramDataRepository programDataRepository;


    public CustomerCard createCustomerCard(CustomerCardRequest request) {
        CustomerCard customerCard = customerCardMapper.apply(request);
        if (request.referredCode() == null
                || customerCardRepository.findByReferralCode(request.referredCode()).isEmpty()) {
            customerCardRepository.save(customerCard);
            return customerCard;
        }

        for (Rule<?> rule : customerCard.getCard().getReferralRules()) {
            ProgramData programData = rule.createProgramData();
            programData.setRule(rule);
            programData.setCustomerCard(customerCard);
            programDataRepository.save(programData);
            customerCard.getProgramsData().add(programData);
        }
        customerCardRepository.save(customerCard);

        return customerCard;
    }


    public void deleteCustomerCard(long customerCardId) {
        customerCardRepository.deleteById(customerCardId);
    }
}
