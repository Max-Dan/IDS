package it.unicam.cs.ids.lp.activity.campaign;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    boolean existsByActivityCard_Activities_Name(String name);
}
