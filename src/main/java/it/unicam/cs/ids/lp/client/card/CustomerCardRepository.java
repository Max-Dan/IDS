package it.unicam.cs.ids.lp.client.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCardRepository
        extends JpaRepository<CustomerCard, Long> {

    @Query("SELECT cc FROM CustomerCard cc WHERE cc.customer.name = :customerName AND cc.program = :program")
    CustomerCard findByCustomerNameAndProgram(String customerName, CustomerCard.CardProgram program);
}
