package it.unicam.cs.ids.lp.activity.campaign;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Campaign findByCard_Activities_Id(long id);

    boolean existsByCard_Activities_Name(String name);
}
