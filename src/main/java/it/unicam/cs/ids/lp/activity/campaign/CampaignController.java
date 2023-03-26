package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
        Objects.requireNonNull(campaignRequest.rules());
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
        if (campaignRequest.end() != null
                && campaign.getEnd() != null
                && campaignRequest.end().isAfter(campaign.getEnd()))
            campaign.setEnd(campaignRequest.end());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/applyRules")
    public ResponseEntity<List<String>> applyRules(@PathVariable long activityId, @RequestBody CustomerOrder order) {
        Campaign campaign = campaignRepository.findByCard_Activities_Id(activityId);
        return ResponseEntity.ok().body(
                campaign.getRules().stream()
                        .map(rule -> "" + rule.getClass().getName() + "\t" + rule.apply(order))
                        .toList()
        );
    }
}
