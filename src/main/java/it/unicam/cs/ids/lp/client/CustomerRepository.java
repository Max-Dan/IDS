package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.login.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, UserRepository<Customer> {
    boolean existsByEmail(String email);

    @Override
    Optional<Customer> findById(Long aLong);
}

