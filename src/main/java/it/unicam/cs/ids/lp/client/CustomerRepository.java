package it.unicam.cs.ids.lp.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    @Override
    Optional<Customer> findById(Long aLong);
}

