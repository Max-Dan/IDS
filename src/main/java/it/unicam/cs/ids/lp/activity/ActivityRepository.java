package it.unicam.cs.ids.lp.activity;

import it.unicam.cs.ids.lp.activity.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository
        extends JpaRepository<Activity, Long> {
    boolean existsByCard(Card card);

    Activity findByName(String name);
}
