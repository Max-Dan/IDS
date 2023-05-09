package it.unicam.cs.ids.lp.activity.card;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CardServiceTest {

    @Autowired
    private CardService cardService;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CardRepository cardRepository;

    @Test
    void createCard() {
        Activity activity = activityRepository.save(new Activity());
        CardRequest cardRequest = new CardRequest("richiesta");
        Card card = cardService.createCard(activity.getId(), cardRequest);
        Assertions.assertTrue(cardRepository.existsById(card.getId()));
        Assertions.assertTrue(activityRepository.existsByCard(card));
        Assertions.assertTrue(card.getActivities().contains(activity));
    }

    @Test
    void allyToCard() {
        Activity activity = activityRepository.save(new Activity());
        CardRequest cardRequest = new CardRequest("richiesta");
        Card card = cardService.createCard(activity.getId(), cardRequest);

        Activity activity2 = activityRepository.save(new Activity());
        cardService.allyToCard(activity2.getId(), card.getId());
        activity2 = activityRepository.findById(activity2.getId()).orElseThrow();
        Assertions.assertNotNull(activity2.getCard());
        Assertions.assertEquals(card, activity2.getCard());
        Assertions.assertEquals(2, cardRepository.findById(card.getId()).orElseThrow()
                .getActivities().size());
    }

    @Test
    void removeCard() {
        Activity activity = activityRepository.save(new Activity());
        CardRequest cardRequest = new CardRequest("richiesta");
        Card card = cardService.createCard(activity.getId(), cardRequest);
        cardService.removeCard(activity.getId());
        Assertions.assertNull(activity.getCard());
        Assertions.assertFalse(cardRepository.existsById(card.getId()));
    }
}
