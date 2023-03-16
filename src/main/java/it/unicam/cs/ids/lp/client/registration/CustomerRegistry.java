package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;

public interface CustomerRegistry<C extends Customer> {

    /**
     * Registra il customer nel database
     *
     * @param customer il customer da registrare
     * @return true se il customer è stata registrato con successo, false altrimenti
     */
    boolean registerCustomer(C customer);

    /**
     * Disiscrive il customer nel database
     *
     * @param customerId id del customer
     */
    void unregisterCustomer(long customerId);
}
