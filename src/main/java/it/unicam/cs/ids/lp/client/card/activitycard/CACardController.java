package it.unicam.cs.ids.lp.client.card.activitycard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/customerActivityCard")
public class CACardController {

    private final CACardRepository caCardRepository;
    private final CACardMapper caCardMapper;

    @Autowired
    public CACardController(CACardRepository caCardRepository, CACardMapper caCardMapper) {
        this.caCardRepository = caCardRepository;
        this.caCardMapper = caCardMapper;
    }

    @PostMapping("/createCard")
    public ResponseEntity<CustomerActivityCard> createCustomerActivityCard(@RequestBody CACardRequest request) {
        CustomerActivityCard customerActivityCard = caCardMapper.apply(request);

        // Salva la CustomerActivityCard per generare un ID
        CustomerActivityCard savedCACard = caCardRepository.save(customerActivityCard);

        // Genera e imposta un codice di riferimento univoco per la CustomerActivityCard
        String referralCode = generateUniqueReferralCode(savedCACard.getId());
        savedCACard.setReferralCode(referralCode);

        // Imposta il campo del link di riferimento utilizzato e incrementa il conteggio dei riferimenti per l'account che lo ha fornito
        String referred = savedCACard.getReferred();
        if (referred != null && !referred.isEmpty()) {
            Optional<CustomerActivityCard> referredCACardOpt = caCardRepository.findByReferralCode(referred);
            if (referredCACardOpt.isPresent()) {
                CustomerActivityCard referredCACard = referredCACardOpt.get();
                referredCACard.setReferredTo(referredCACard.getReferredTo() + 1);
                caCardRepository.save(referredCACard);
            }
        }

        // Salva la CustomerActivityCard aggiornata
        caCardRepository.save(savedCACard);

        return new ResponseEntity<>(savedCACard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerActivityCardId}")
    public ResponseEntity<?> deleteCard(@PathVariable long customerActivityCardId) {
        caCardRepository.deleteById(customerActivityCardId);
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
