package it.unicam.cs.ids.lp.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordStorage extends JpaRepository<Password, Long> {

    Password findByUsername(String username);
}
