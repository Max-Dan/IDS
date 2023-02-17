package it.unicam.cs.ids.lp.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCardRepository
        extends JpaRepository<CustomerCard, String> {
}

