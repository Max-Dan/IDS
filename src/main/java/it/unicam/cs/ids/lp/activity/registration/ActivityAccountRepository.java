package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.ActivityAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityAccountRepository
        extends JpaRepository<ActivityAccount, String> {
}
