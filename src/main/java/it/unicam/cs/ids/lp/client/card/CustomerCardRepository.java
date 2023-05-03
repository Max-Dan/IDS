package it.unicam.cs.ids.lp.client.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerCardRepository
        extends JpaRepository<CustomerCard, CustomerCardCompositeId> {

    Optional<CustomerCard> findByReferralCode(String referralCode);
}
