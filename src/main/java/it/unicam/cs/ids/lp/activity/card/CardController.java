package it.unicam.cs.ids.lp.activity.card;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity/{activityId}/card")
public class CardController {

    private final CardService cardService;

    @PutMapping("/addCard")
    public ResponseEntity<Card> createCampaign(@PathVariable long activityId, @RequestBody CardRequest cardRequest) {
        Card card = cardService.createCard(activityId, cardRequest);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/removeCard")
    public ResponseEntity<?> removeCard(@PathVariable long activityId) {
        cardService.removeCard(activityId);
        return ResponseEntity.ok("");
    }

    @PostMapping("/allyToCard/{cardId}")
    public ResponseEntity<?> allyToCard(@PathVariable long activityId, @PathVariable long cardId) {
        cardService.allyToCard(activityId, cardId);
        return ResponseEntity.ok("");
    }
}
