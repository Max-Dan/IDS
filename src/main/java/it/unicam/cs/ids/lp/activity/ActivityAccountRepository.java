package it.unicam.cs.ids.lp.activity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityAccountRepository
        extends JpaRepository<ActivityAccount, String> {
}
