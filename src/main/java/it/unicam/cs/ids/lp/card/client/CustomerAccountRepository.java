package it.unicam.cs.ids.lp.card.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository
        extends JpaRepository<CustomerAccount, String> {
}
