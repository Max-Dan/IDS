package it.unicam.cs.ids.lp.client.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCardRepository
        extends JpaRepository<CustomerCard, Long> {

    CustomerCard findByCustomer_Id(long id);

}
