package it.unicam.cs.ids.lp.util;

import java.util.Optional;

/**
 * Permette ad un entità di iscriversi e disiscriversi dal sistema
 */
public interface Registry<T> {

    /**
     * Registra un entità nel database
     *
     * @param entity l'entità
     * @return l'attività salvata
     */
    Optional<T> register(T entity);

    /**
     * Disiscrive un entità nel database
     *
     * @param entity l'entità
     */
    void unregister(T entity);
}
