package it.unicam.cs.ids.lp.client.card.programs;

import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.rules.Rule;
import org.springframework.stereotype.Component;

@Component
public class ProgramDataMapper {

    public ProgramData map(Rule<?> rule, CustomerCard customerCard) {
        ProgramData programData = rule.createProgramData();
        programData.setRule(rule);
        programData.setCustomerCard(customerCard);
        return programData;
    }
}
