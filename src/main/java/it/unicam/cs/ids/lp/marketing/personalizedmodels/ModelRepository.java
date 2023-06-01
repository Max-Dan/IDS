package it.unicam.cs.ids.lp.marketing.personalizedmodels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<MessageModel, Long> {

    Optional<MessageModel> findById(long id);

    Optional<MessageModel> findByModelName(String modelName);
}

