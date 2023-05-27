package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.client.card.programs.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customerCard")
public class UpdateCardController {

    private final CustomerCardRepository customerCardRepository;

    private final ProgramDataMapper programDataMapper;
    private final ProgramDataRepository programDataRepository;
    private final CashbackDataRepository cashbackDataRepository;

    @PutMapping("/modifyProgram")
    public ResponseEntity<?> modifyProgram(@RequestBody CustomerCardUpdateRequest request) {
        Optional<CustomerCard> optionalCard = customerCardRepository.findById(request.getCustomerCardId());

        if (optionalCard.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerCard customerCard = optionalCard.get();

        ProgramData programData = programDataMapper.map(request.getProgramRule(), customerCard);
        boolean programExists = customerCard.getProgramsData().stream()
                .anyMatch(program -> program.getRule().getClass() == request.getProgramRule().getClass());

        if (programExists) {
            customerCard.getProgramsData().stream()
                    .filter(pd -> pd.getClass() == programData.getClass())
                    .findFirst()
                    .ifPresent(pd -> {
                        customerCard.getProgramsData().remove(pd);
                        customerCardRepository.save(customerCard);
                        programDataRepository.delete(pd);
                    });
        } else {
            programDataRepository.save(programData);
            customerCard.getProgramsData().add(programData);
            customerCardRepository.save(customerCard);
        }
        System.out.println("Card program modified: " + customerCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/modifyAttributes")
    public ResponseEntity<?> modifyAttributes(@RequestBody CustomerCardUpdateRequest request) {
        Optional<CustomerCard> optionalCard = customerCardRepository.findById(request.getCustomerCardId());

        if (optionalCard.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CustomerCard customerCard = optionalCard.get();
        customerCard.setFamily(request.isFamily());
        customerCard.getProgramsData().stream().forEach(programData -> {
            if (programData instanceof CashbackData cashbackData) {
                System.out.println("GOES");
                if (request.getRemainingCashback() != 0) {
                    System.out.println("GOES");
                    cashbackData.setRemainingCashback(request.getRemainingCashback());
                    programDataRepository.save(programData);
                }
            }
        });

        /*dato che abbiamo creato solo CashbackData  viene gestita solo quella variabile, ma bisognerà
          estendere lo stream per ogni programma esistente*/
        customerCardRepository.save(customerCard);
        System.out.println("Card attributes modified: " + customerCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
