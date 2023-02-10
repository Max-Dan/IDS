package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityAccount;

public interface ActivityRegistry<A extends Activity, Account extends ActivityAccount> {

    /**
     * Registra l'attività nel database
     *
     * @param activity        l'attività da registrare
     * @param activityAccount il profilo dell'attività da registrare
     * @return true se l'attività è stata registrata con successo, false altrimenti
     */
    boolean registerActivity(A activity, Account activityAccount);

    /**
     * Disiscrive l'attività nel database
     *
     * @param name il nome dell'attività
     */
    void unregisterActivityByName(String name);
}
