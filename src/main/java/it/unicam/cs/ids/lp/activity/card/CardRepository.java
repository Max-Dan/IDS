package it.unicam.cs.ids.lp.activity.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByActivities_Id(long id);

    Optional<Card> findByActivities_Id(long id);
}
