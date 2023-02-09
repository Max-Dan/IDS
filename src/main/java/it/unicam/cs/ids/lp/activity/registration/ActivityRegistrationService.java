package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityAccount;
import it.unicam.cs.ids.lp.activity.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ActivityRegistrationService
        implements ActivityDataValidator<Activity>, ActivityRecorder<Activity, ActivityAccount> {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityAccountRepository activityAccountRepository;

    @Override
    public boolean registerActivity(Activity activity, ActivityAccount activityAccount) {
        if (!areActivityValuesValid(activity))
            return false;
        activityRepository.save(activity);
        activityAccountRepository.save(activityAccount);
        return true;
    }

    @Override
    public boolean areActivityValuesValid(Activity activity) {
        Objects.requireNonNull(activity);
        return isNameValid(activity.getName())
                && isAddressValid(activity.getAddress())
                && isTelephoneNumberValid(activity.getTelephoneNumber())
                && isEmailValid(activity.getEmail())
                && isCategoryValid(activity.getCategory());
    }

    /**
     * Verifica che il nome sia valido
     *
     * @param name il nome da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    protected boolean isNameValid(String name) {
        Objects.requireNonNull(name);
        return name.length() > 0
                && name.length() < 255
                && !name.contains("\\.[]{}()<>*+-=!?^$|");
    }

    /**
     * Verifica che l'indirizzo sia valido
     *
     * @param address l'indirizzo da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    protected boolean isAddressValid(String address) {
        return isNameValid(address);
    }

    /**
     * Verifica che il numero di telefono sia valido
     *
     * @param telephoneNumber il numero da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    protected boolean isTelephoneNumberValid(String telephoneNumber) {
        Objects.requireNonNull(telephoneNumber);
        return telephoneNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
    }

    /**
     * Verifica che l'email sia valida
     *
     * @param email l'email
     * @return true se l'email è valida, false altrimenti
     */
    protected boolean isEmailValid(String email) {
        Objects.requireNonNull(email);
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    /**
     * Verifica che la categoria sia valida
     *
     * @param category la categoria da verificare
     * @return true se la categoria è corretta, false altrimenti
     */
    private boolean isCategoryValid(ContentCategory category) {
        Objects.requireNonNull(category);
        //TODO controllarla meglio
        return true;
    }
}
