package it.unicam.cs.ids.lp.activity.purchase;

import it.unicam.cs.ids.lp.client.card.programs.ProgramData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity/{activityId}")
public class PurchasesController {

    private final PurchaseService purchaseService;

    @GetMapping("/checkBonus/{customerCardId}")
    public ResponseEntity<List<String>> checkBonusFromCard(@PathVariable long activityId,
                                                           @PathVariable long customerCardId,
                                                           @RequestBody PurchaseProducts products) {
        List<String> strings = purchaseService.checkBonus(activityId, customerCardId, products.productIds());
        return ResponseEntity.ok(strings);
    }

    @GetMapping("/applyBonus/{customerCardId}")
    public ResponseEntity<?> applyBonusFromCard(@PathVariable long activityId,
                                                @PathVariable long customerCardId,
                                                @RequestBody PurchaseProducts products) {
        List<ProgramData> programData = purchaseService.applyBonus(activityId, customerCardId, products.productIds());
        return ResponseEntity.ok(programData);
    }

    private record PurchaseProducts(List<Long> productIds) {
    }
}
