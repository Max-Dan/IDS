package it.unicam.cs.ids.lp.login;

import java.util.Optional;

public interface UserRepository<T> {
    Optional<T> findByEmail(String email);
}

