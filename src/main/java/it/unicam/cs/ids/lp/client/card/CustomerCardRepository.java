package it.unicam.cs.ids.lp.client.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerCardRepository
        extends JpaRepository<CustomerCard, Long> {

    CustomerCard findByCustomer_Id(long id);

    Optional<CustomerCard> findByReferralCode(String referralCode);

}
