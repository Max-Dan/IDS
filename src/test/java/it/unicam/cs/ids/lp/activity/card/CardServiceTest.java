package it.unicam.cs.ids.lp.activity.card;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.registration.ActivityRegistrationService;
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
    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @Test
    void createCard() {
        Activity activity = activityRegistrationService.register(new Activity()).orElseThrow();
        CardRequest cardRequest = new CardRequest("richiesta");
        Card card = cardService.createCard(activity.getId(), cardRequest);
        Assertions.assertTrue(cardRepository.existsById(card.getId()));
        Assertions.assertTrue(activityRepository.existsByCard(card));
        Assertions.assertTrue(card.getActivities().contains(activity));
    }

    @Test
    void allyToCard() {
        Activity activity = activityRegistrationService.register(new Activity()).orElseThrow();
        Card card = cardService.createCard(activity.getId(), new CardRequest("richiesta"));

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

        Card card = cardService.createCard(activity.getId(), new CardRequest("richiesta"));
        cardService.removeCard(activity.getId());
        Assertions.assertNull(activity.getCard());
        Assertions.assertFalse(cardRepository.existsById(card.getId()));
    }
}
