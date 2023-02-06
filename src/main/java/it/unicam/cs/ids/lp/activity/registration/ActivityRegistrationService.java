package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityRegistrationService implements ActivityDataValidator<Activity> {

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Registra l'attività nel database
     *
     * @param activity l'attività da registrare
     */
    public void registerActivity(Activity activity) {
        if (checkActivityValues(activity))
            activityRepository.save(activity);
    }

    @Override
    public boolean checkActivityValues(Activity activity) {
        return !isAlreadyUsed(activity) && !checkTelephoneNumber(activity.getTelephoneNumber());
    }

    /**
     * Verifica che il numero di telefono sia valido
     *
     * @param telephoneNumber il numero da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    private boolean checkTelephoneNumber(String telephoneNumber) {
        return telephoneNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
    }

    /**
     * Verifica che il nome dell'attività è già stato utilizzato
     *
     * @param activity l'attività da controllare
     * @return true se il nome è già stato utilizzato
     */
    private boolean isAlreadyUsed(Activity activity) {
        return !activityRepository.findById(activity.getName())
                .equals(Optional.empty());
    }
}
