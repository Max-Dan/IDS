package it.unicam.cs.ids.lp.activity.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity/campaign")
public class CampaignController {
    //@Autowired
    //private CardRepository cardRepository;
    @Autowired
    private CampaignMapper campaignMapper;

    @PostMapping("add/{activityId}")
    public ResponseEntity<?> createCampaign(@PathVariable long activityId, @RequestBody CampaignRequest campaignRequest) {
//        Campaign campaign = campaignMapper.apply(campaignRequest, card);
//        card.setCampaign(campaign);
//        campaignRepository.save(campaign);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("modifyData/{campaignId}")
    public ResponseEntity<?> modifyCampaign(@PathVariable long campaignId, @RequestBody CampaignRequest campaignRequest) {
        // TODO implementare
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
