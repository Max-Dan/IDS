package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity/{activityId}/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/addCampaign")
    public ResponseEntity<Campaign> createCampaign(@PathVariable long activityId, @RequestBody CampaignRequest campaignRequest) {
        Campaign campaign = campaignService.createCampaign(activityId, campaignRequest);
        return campaign != null ?
                ResponseEntity.ok(campaign)
                : ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/{campaignId}/modifyData")
    public ResponseEntity<?> modifyCampaign(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CampaignRequest campaignRequest) {
        Campaign campaign = campaignService.modifyCampaign(campaignId, activityId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @PostMapping("/{campaignId}/applyRules")
    public ResponseEntity<List<String>> applyRules(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CustomerOrder order) {
        List<String> strings = campaignService.applyRules(campaignId, activityId, order);
        return ResponseEntity.ok().body(strings);
    }

    @GetMapping("/getCampaigns")
    public ResponseEntity<?> getActiveCampaigns(@PathVariable long activityId) {
        return ResponseEntity.ok(campaignService.getActiveCampaigns(activityId));
    }
}
