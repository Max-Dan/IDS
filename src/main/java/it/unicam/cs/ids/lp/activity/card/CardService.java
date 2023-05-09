package it.unicam.cs.ids.lp.activity.card;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final ActivityRepository activityRepository;
    private final CardMapper cardMapper;
    private final CardRepository cardRepository;

    /**
     * Crea una carta all'attività
     *
     * @param activityId  id dell'attività
     * @param cardRequest dati della carta
     * @return la carta creata
     */
    public Card createCard(long activityId, CardRequest cardRequest) {
        Activity activity = activityRepository.findById(activityId).orElseThrow();
        Card card = cardMapper.apply(cardRequest);
        card.getActivities().add(activity);
        cardRepository.save(card);
        activity.setCard(card);
        activityRepository.save(activity);
        return card;
    }

    /**
     * Permette a un'attività di allearsi in una carta
     *
     * @param activityId id dell'attività
     * @param cardId     id della carta
     */
    public void allyToCard(long activityId, long cardId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow();
        if (activity.getCard() != null)
            throw new RuntimeException();
        Card card = cardRepository.findById(cardId).orElseThrow();
        card.getActivities().add(activity);
        cardRepository.save(card);
        activity.setCard(card);
        activityRepository.save(activity);
    }

    /**
     * Rimuove la carta all'attività
     *
     * @param activityId id dell'attività
     */
    public void removeCard(long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow();
        if (activity.getCard() == null)
            throw new RuntimeException();
        cardRepository.deleteById(activity.getCard().getId());
        activity.setCard(null);
        activityRepository.save(activity);
    }
}
