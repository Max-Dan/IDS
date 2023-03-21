package it.unicam.cs.ids.lp.client.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/customerCard")
public class CustomerCardController {

    private final CustomerCardRepository repository;
    private final CustomerCardMapper customerCardMapper;

    @Autowired
    public CustomerCardController(CustomerCardRepository repository, CustomerCardMapper customerCardMapper) {
        this.repository = repository;
        this.customerCardMapper = customerCardMapper;
    }

    @PostMapping("/createCard")
    public ResponseEntity<CustomerCard> createCustomerCard(@RequestBody CustomerCardRequest request) {
        CustomerCard customerCard = customerCardMapper.apply(request);

        // Salva la CustomerCard per generare un ID
        CustomerCard savedCard = repository.save(customerCard);

        // Genera e imposta un codice di riferimento univoco per la CustomerCard
        String referralCode = generateUniqueReferralCode(savedCard.getId());
        savedCard.setReferralCode(referralCode);

        // Imposta il campo del link di riferimento utilizzato
        String referred = savedCard.getReferred();
        if (referred != null && !referred.isEmpty()) {
            Optional<CustomerCard> referredCardOpt = repository.findByReferralCode(referred);
        }

        // Salva la CustomerActivityCard aggiornata
        repository.save(savedCard);
        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerCardId}")
    public ResponseEntity<?> deleteCard(@PathVariable long customerCardId) {
        repository.deleteById(customerCardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String generateUniqueReferralCode(Long customerId) {
        // Caratteri disponibili per creare il codice di riferimento alfanumerico
        String base62Chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        // Inizializza un oggetto StringBuilder per creare il codice di riferimento
        StringBuilder referralCode = new StringBuilder();
        Random random = new Random();

        // Converte l'ID del cliente in una stringa e lo aggiunge al codice di riferimento
        String customerIdStr = Long.toString(customerId);
        referralCode.append(customerIdStr);

        // Calcola e riempie il referal con caratteri alfanumerici
        int remainingChars = 16 - customerIdStr.length();
        for (int i = 0; i < remainingChars; i++) {
            int randomIndex = random.nextInt(base62Chars.length());
            referralCode.append(base62Chars.charAt(randomIndex));
        }

        // Restituisce il codice di riferimento generato come stringa
        return referralCode.toString();
    }
}
