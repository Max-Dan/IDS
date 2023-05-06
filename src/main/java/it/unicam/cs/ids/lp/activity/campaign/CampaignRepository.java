package it.unicam.cs.ids.lp.activity.campaign;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByCard_Activities_Id(long id);


    boolean existsByCard_Activities_Id(long id);

}
