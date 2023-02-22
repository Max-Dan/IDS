package it.unicam.cs.ids.lp.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository
        extends JpaRepository<CustomerAccount, Customer> {
}
