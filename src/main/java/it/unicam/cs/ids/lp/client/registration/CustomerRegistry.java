package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerAccount;
import it.unicam.cs.ids.lp.client.card.CustomerCard;

public interface CustomerRegistry<C extends Customer, Account extends CustomerAccount, CSC extends CustomerCard> {

    /**
     * Registra il customer nel database
     *
     * @param customer        il customer da registrare
     * @param customerAccount il profilo del customer da registrare
     * @return true se il customer è stata registrato con successo, false altrimenti
     */
    boolean registerCustomer(C customer, Account customerAccount, CSC customerCard);

    /**
     * Disiscrive il customer nel database
     *
     * @param name il nome del customer
     */
    void unregisterCustomerByName(String name);
}
