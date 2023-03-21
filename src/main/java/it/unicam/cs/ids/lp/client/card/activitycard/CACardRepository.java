package it.unicam.cs.ids.lp.client.card.activitycard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CACardRepository extends JpaRepository<CustomerActivityCard, Long> {

    Optional<CustomerActivityCard> findByReferralCode(String referralCode);

}
