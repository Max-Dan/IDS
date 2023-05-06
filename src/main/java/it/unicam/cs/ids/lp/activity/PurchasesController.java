package it.unicam.cs.ids.lp.activity;

import it.unicam.cs.ids.lp.activity.campaign.CampaignService;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.client.order.CustomerOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity/{activityId}")
public class PurchasesController {

    private final CustomerCardRepository customerCardRepository;

    private final CampaignService campaignService;

    private final CustomerOrderMapper customerOrderMapper;

    private final ProductRepository productRepository;

    @GetMapping("/checkBonus/{customerCardId}")
    public ResponseEntity<?> checkBonusFromCard(@PathVariable long activityId,
                                                @PathVariable long customerCardId,
                                                @RequestBody Set<Long> productIds) {
        CustomerCard customerCard;
        try {
            customerCard = customerCardRepository.findById(customerCardId).orElseThrow();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("La tessera non esiste");
        }
        if (customerCard.getCard().getActivities().stream().anyMatch(activity -> activity.getId() != activityId))
            return ResponseEntity.badRequest().body("La tessera non appartiene a questa attività");

        CustomerOrder order = customerOrderMapper.apply(new HashSet<>(productRepository.findAllById(productIds)),
                customerCard.getCustomer());

        List<String> list = customerCard.getCampaigns()
                .stream()
                .filter(campaign -> !campaign.isCurrentlyActive())
                .map(campaign -> campaignService.seeBonuses(campaign.getId(), order))
                .flatMap(List::stream)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/applyBonus/{customerCardId}")
    public ResponseEntity<?> applyBonusFromCard(@PathVariable long activityId,
                                                @PathVariable long customerCardId,
                                                @RequestBody Set<Long> productIds) {
        CustomerCard customerCard;
        try {
            customerCard = customerCardRepository.findById(customerCardId).orElseThrow();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("La tessera non esiste");
        }
        if (customerCard.getCard().getActivities().stream().anyMatch(activity -> activity.getId() != activityId))
            return ResponseEntity.badRequest().body("La tessera non appartiene a questa attività");

        CustomerOrder order = customerOrderMapper.apply(new HashSet<>(productRepository.findAllById(productIds)),
                customerCard.getCustomer());

        List<String> list = customerCard.getCampaigns()
                .stream()
                .filter(campaign -> !campaign.isCurrentlyActive())
                .map(campaign -> campaignService.applyRules(campaign.getId(), activityId, order))
                .flatMap(List::stream)
                .toList();
        return ResponseEntity.ok(list);
    }
}
