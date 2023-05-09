package it.unicam.cs.ids.lp.activity.card;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.ContentCategory;
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
        Activity activity1 = new Activity();
        activity1.setCategory(ContentCategory.TECHNOLOGY);
        Activity activity = activityRegistrationService.register(activity1).orElseThrow();
        CardRequest cardRequest = new CardRequest("richiesta");
        Card card = cardService.createCard(activity.getId(), cardRequest);
        Assertions.assertTrue(cardRepository.existsById(card.getId()));
        Assertions.assertTrue(activityRepository.existsByCard(card));
        Assertions.assertTrue(card.getActivities().contains(activity));
    }

    @Test
    void allyToCard() {
        Activity activity1 = new Activity();
        activity1.setCategory(ContentCategory.TECHNOLOGY);
        Activity activity = activityRegistrationService.register(activity1).orElseThrow();
        Card card = cardService.createCard(activity.getId(), new CardRequest("richiesta"));

        activity1 = new Activity();
        activity1.setCategory(ContentCategory.TECHNOLOGY);
        Activity activity2 = activityRepository.save(activity1);

        cardService.allyToCard(activity2.getId(), card.getId());
        activity2 = activityRepository.findById(activity2.getId()).orElseThrow();
        Assertions.assertNotNull(activity2.getCard());
        Assertions.assertEquals(card, activity2.getCard());
        Assertions.assertEquals(2, cardRepository.findById(card.getId()).orElseThrow()
                .getActivities().size());
    }

    @Test
    void removeCard() {
        Activity activity1 = new Activity();
        activity1.setCategory(ContentCategory.TECHNOLOGY);
        Activity activity = activityRepository.save(activity1);

        Card card = cardService.createCard(activity.getId(), new CardRequest("richiesta"));
        cardService.removeCard(activity.getId());
        Assertions.assertNull(activity.getCard());
        Assertions.assertFalse(cardRepository.existsById(card.getId()));
    }
}
