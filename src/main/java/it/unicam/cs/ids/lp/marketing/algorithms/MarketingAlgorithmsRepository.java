package it.unicam.cs.ids.lp.marketing.algorithms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketingAlgorithmsRepository extends JpaRepository<MarketingAlgorithm, Long> {
    /**
     * Qua vengono registrati gli algoritmi di marketing automation
     * <p>
     * Utilizzando poi la classe stessa di Automation la lista di algoritmi
     * verrà interrogata completamente, e in base all'algoritmo e alle caratteristiche i messaggi verranno mandati
     * Prima della spedizione vediamo se l'algoritmo non è scaduto, viene effettuato un check
     * e se la data di scadenza è passata allora il messaggio non verrà mandato
     */
    Optional<MarketingAlgorithm> findById(long id);


    Optional<MarketingAlgorithm> findByName(String algorithmName);
}

