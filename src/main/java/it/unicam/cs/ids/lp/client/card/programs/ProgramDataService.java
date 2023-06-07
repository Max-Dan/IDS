package it.unicam.cs.ids.lp.client.card.programs;

import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.rules.Rule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ProgramDataService {

    private final ProgramDataMapper programDataMapper;
    private final ProgramDataRepository programDataRepository;
    private CustomerCardRepository customerCardRepository;

    /**
     * Se la carta del customer non ha ancora un programdata di una regola della campagna, la crea
     *
     * @param rules        regole dei programmi da salvare
     * @param customerCard id del customer
     */
    public void createNotExistentProgramData(Collection<? extends Rule<?>> rules, CustomerCard customerCard) {
        rules.stream()
                .map(rule -> programDataMapper.map(rule, customerCard))
                .peek(programData -> customerCard.getProgramsData().add(programData))
                .forEach(programDataRepository::save);
        customerCardRepository.save(customerCard);
    }
}
