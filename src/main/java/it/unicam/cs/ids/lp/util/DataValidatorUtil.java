package it.unicam.cs.ids.lp.util;

import it.unicam.cs.ids.lp.activity.ContentCategory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DataValidatorUtil {

    /**
     * Verifica che il nome sia valido
     *
     * @param name il nome da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    public boolean isNameValid(String name) {
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
    public boolean isSurnameValid(String surname) {
        return isNameValid(surname);
    }

    /**
     * Verifica che il numero di telefono sia valido
     *
     * @param telephoneNumber il numero da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    public boolean isTelephoneNumberValid(String telephoneNumber) {
        return telephoneNumber == null
                || telephoneNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
    }

    /**
     * Verifica che l'email sia valida
     *
     * @param email l'email
     * @return true se l'email è valida, false altrimenti
     */
    public boolean isEmailValid(String email) {
        return email == null
                || email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    /**
     * Verifica che la categoria sia valida
     *
     * @param category la categoria da verificare
     * @return true se la categoria è corretta, false altrimenti
     */
    public boolean isCategoryValid(ContentCategory category) {
        Objects.requireNonNull(category);
        return true;
    }

    /**
     * Verifica che l'indirizzo sia valido
     *
     * @param address l'indirizzo da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    public boolean isAddressValid(String address) {
        return isNameValid(address);
    }
}
