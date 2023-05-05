package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity/{activityId}/campaign")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping("/addCampaign")
    public ResponseEntity<Campaign> createCampaign(@PathVariable long activityId, @RequestBody CampaignRequest campaignRequest) {
        Campaign campaign = campaignService.createCampaign(activityId, campaignRequest);
        return campaign != null ?
                ResponseEntity.ok(campaign)
                : ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/{campaignId}/modifyData")
    public ResponseEntity<?> modifyCampaign(@PathVariable long campaignId, @RequestBody CampaignRequest campaignRequest) {
        Campaign campaign = campaignService.modifyCampaign(campaignId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @PostMapping("/{campaignId}/applyRules")
    public ResponseEntity<List<String>> applyRules(@PathVariable long campaignId, @RequestBody CustomerOrder order) {
        List<String> strings = campaignService.applyRules(campaignId, order);
        return ResponseEntity.ok().body(strings);
    }

    @GetMapping("/getCampaigns")
    public ResponseEntity<?> getActiveCampaigns(@PathVariable long activityId) {
        return ResponseEntity.ok(campaignService.getActiveCampaigns(activityId));
    }
}
