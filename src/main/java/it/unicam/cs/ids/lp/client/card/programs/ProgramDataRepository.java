package it.unicam.cs.ids.lp.client.card.programs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProgramDataRepository
        extends JpaRepository<ProgramData, Long> {

}