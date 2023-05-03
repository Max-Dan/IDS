package it.unicam.cs.ids.lp.util;

/**
 * Permette ad un entità di iscriversi e disiscriversi dal sistema
 */
public interface Registry<T> {

    /**
     * Registra un entità nel database
     *
     * @param entity l'entità
     * @return true se registrato con successo, false altrimenti
     */
    boolean register(T entity);

    /**
     * Disiscrive un entità nel database
     *
     * @param entity l'entità
     */
    void unregister(T entity);
}
