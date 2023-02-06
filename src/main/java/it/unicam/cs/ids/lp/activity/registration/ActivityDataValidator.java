package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;

public interface ActivityDataValidator<T extends Activity> {

    /**
     * Verifica se è possibile registrare l'attività
     *
     * @param activity l'attività da registrare
     * @return true se è possibile registrare l'attività, false altrimenti
     */
    boolean areActivityValuesValid(T activity);
}
