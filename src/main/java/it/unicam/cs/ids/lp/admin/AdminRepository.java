package it.unicam.cs.ids.lp.admin;

import it.unicam.cs.ids.lp.login.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, UserRepository<Admin> {
    boolean existsByEmail(String email);

    @Override
    Optional<Admin> findById(Long aLong);
}

