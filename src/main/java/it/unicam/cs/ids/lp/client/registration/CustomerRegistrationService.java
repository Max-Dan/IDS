package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerRegistrationService
        implements CustomerRegistry<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean registerCustomer(Customer customer) {
        Objects.requireNonNull(customer);
        if (customerRepository.existsByEmail(customer.getEmail())
                || !areRegistrationValuesValid(customer))
            return false;

        // Salva il cliente per avere un id generato
        Customer savedCustomer = customerRepository.save(customer);

        // Genera e setta un referal unico per il customer
        String referralCode = generateUniqueReferralCode(savedCustomer.getId());
        savedCustomer.setReferralCode(referralCode);

        // Setta il campo del referal link usato e incrementa il campo di clienti invitati tramite referal all'account che lo ha fornito
        String referred = customer.getReferred();
        if (referred != null && !referred.isEmpty()) {
            Optional<Customer> referredCustomerOpt = customerRepository.findByReferralCode(referred);
            if (referredCustomerOpt.isPresent()) {
                Customer referredCustomer = referredCustomerOpt.get();
                referredCustomer.setReferredTo(referredCustomer.getReferredTo() + 1);
                customerRepository.save(referredCustomer);
            } // Se il referal è invalido o non esiste, la registrazione prosegue dato che non è obbligatorio
        }

        customerRepository.save(savedCustomer);
        return true;
    }

    public boolean areRegistrationValuesValid(Customer customer) {
        return isNameValid(customer.getName())
                && isSurnameValid(customer.getSurname())
                && isTelephoneNumberValid(customer.getTelephoneNumber())
                && isEmailValid(customer.getEmail());
    }

    /**
     * Verifica che il nome sia valido
     *
     * @param name il nome da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    protected boolean isNameValid(String name) {
        return name == null
                || name.length() > 0
                && name.length() < 255
                && !name.contains("\\.[]{}()<>*+-=!?^$|");
    }

    /**
     * Verifica che il cognome sia valido
     *
     * @param surname cognome da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    protected boolean isSurnameValid(String surname) {
        return isNameValid(surname);
    }

    /**
     * Verifica che il numero di telefono sia valido
     *
     * @param telephoneNumber il numero da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    protected boolean isTelephoneNumberValid(String telephoneNumber) {
        return telephoneNumber == null
                || telephoneNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
    }

    /**
     * Verifica che l'email sia valida
     *
     * @param email l'email
     * @return true se l'email è valida, false altrimenti
     */
    protected boolean isEmailValid(String email) {
        return email == null
                || email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public void unregisterCustomer(long customerId) {
        customerRepository.deleteById(customerId);
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
