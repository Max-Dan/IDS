package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.activity.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long> {
    long countByCards_Card(Card card);

    boolean existsByEmail(String email);
}

