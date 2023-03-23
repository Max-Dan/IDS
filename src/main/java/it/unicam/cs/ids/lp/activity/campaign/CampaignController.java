package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/activity/{activityId}/campaign")
public class CampaignController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CampaignMapper campaignMapper;
    @Autowired
    private CampaignRepository campaignRepository;

    @PostMapping("/addCampaign")
    public ResponseEntity<?> createCampaign(@PathVariable long activityId, @RequestBody CampaignRequest campaignRequest) {
        Card card;
        try {
            card = cardRepository.findByActivities_Id(activityId).orElseThrow();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Attività non ha ancora una carta dove inserire la campagna");
        }
        Campaign campaign = campaignMapper.apply(campaignRequest, card);
        card.setCampaign(campaign);
        campaignRepository.save(campaign);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/modifyData")
    public ResponseEntity<?> modifyCampaign(@PathVariable long activityId, @RequestBody CampaignRequest campaignRequest) {
        Campaign campaign = campaignRepository.getReferenceById(
                cardRepository.findByActivities_Id(activityId).orElseThrow().getCampaign().getId());
        if (campaignRequest.description() != null)
            campaign.setDescription(campaignRequest.description());
        if (campaignRequest.shopUrl() != null)
            campaign.setShopUrl(campaignRequest.shopUrl());
        if (campaignRequest.category() != null)
            campaign.setCategory(campaignRequest.category());
        if (campaignRequest.end() != null)
            campaign.setEnd(campaignRequest.end());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
