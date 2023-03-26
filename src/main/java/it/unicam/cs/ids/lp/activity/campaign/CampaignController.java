package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity/{activityId}/campaign")
public class CampaignController {
    @Autowired
    private CampaignService campaignService;

    @PostMapping("/addCampaign")
    public ResponseEntity<Campaign> createCampaign(@PathVariable long activityId, @RequestBody CampaignRequest campaignRequest) {
        Campaign campaign = campaignService.createCampaign(activityId, campaignRequest);
        return campaign != null ?
                ResponseEntity.ok(campaign)
                : ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/modifyData")
    public ResponseEntity<?> modifyCampaign(@PathVariable long activityId, @RequestBody CampaignRequest campaignRequest) {
        Campaign campaign = campaignService.modifyCampaign(activityId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @PostMapping("/applyRules")
    public ResponseEntity<List<String>> applyRules(@PathVariable long activityId, @RequestBody CustomerOrder order) {
        List<String> strings = campaignService.applyRules(activityId, order);
        return ResponseEntity.ok().body(strings);
    }
}
