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

    @GetMapping("/{campaignId}/terminate")
    public ResponseEntity<?> terminateCampaign(@PathVariable long campaignId, @PathVariable long activityId) {
        campaignService.terminateCampaign(campaignId, activityId);
        return ResponseEntity.ok("");
    }

    @PostMapping("/{campaignId}/modifyData")
    public ResponseEntity<?> modifyCampaign(@PathVariable long campaignId, @RequestBody CampaignRequest campaignRequest, @PathVariable long activityId) {
        Campaign campaign = campaignService.modifyCampaign(campaignId, activityId, campaignRequest);
        return ResponseEntity.ok(campaign);
    }

    @PostMapping("/{campaignId}/seeBonuses")
    public ResponseEntity<List<String>> seeBonuses(@PathVariable long campaignId, @RequestBody CustomerOrder order, @PathVariable long activityId) {
        return ResponseEntity.ok()
                .body(campaignService.seeBonuses(campaignId, activityId, order));
    }

    @PostMapping("/{campaignId}/applyRules")
    public ResponseEntity<String> applyRules(@PathVariable long campaignId, @RequestBody CustomerOrder order, @PathVariable long activityId) {
        campaignService.applyRules(campaignId, activityId, order);
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/getAllCampaigns")
    public ResponseEntity<?> getAllCampaigns(@PathVariable long activityId) {
        return ResponseEntity.ok(campaignService.getAllCampaigns(activityId));
    }

    @GetMapping("/getCampaigns")
    public ResponseEntity<?> getActiveCampaigns(@PathVariable long activityId) {
        return ResponseEntity.ok(campaignService.getActiveCampaigns(activityId));
    }
}
