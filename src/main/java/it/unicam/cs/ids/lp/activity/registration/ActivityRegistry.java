package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;

public interface ActivityRegistry<A extends Activity, ID> {

    /**
     * Registra l'attività nel database
     *
     * @param activity l'attività da registrare
     * @return true se l'attività è stata registrata con successo, false altrimenti
     */
    boolean registerActivity(A activity);

    /**
     * Disiscrive l'attività nel database
     *
     * @param id l'id dell'attività
     */
    void unregisterActivityById(ID id);
}
