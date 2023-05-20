package it.unicam.cs.ids.lp.client.card.programs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashbackDataRepository
        extends JpaRepository<CashbackData, Long> {
}
