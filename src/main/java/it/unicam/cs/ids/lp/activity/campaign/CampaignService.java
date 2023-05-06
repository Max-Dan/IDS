package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.campaign.rules.AbstractRuleRepository;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignService {

    private final CardRepository cardRepository;
    private final CampaignMapper campaignMapper;
    private final CampaignRepository campaignRepository;
    private final AbstractRuleRepository<?> abstractRuleRepository;

    /**
     * Crea una campagna associata alla carta dell'attività
     *
     * @param activityId      id dell'attività che vuole creare una campagna
     * @param campaignRequest informazioni richieste per creare le campagne
     * @return la campagna creata
     */
    public Campaign createCampaign(long activityId, CampaignRequest campaignRequest) {
        Card card = cardRepository.findByActivities_Id(activityId).orElseThrow();
        Campaign campaign = campaignMapper.apply(campaignRequest, card);
        campaignRepository.save(campaign);
        return campaign;
    }

    /**
     * Modifica i dati di una campagna esistente
     *
     * @param campaignId      id della campagna da modificare
     * @param campaignRequest dati aggiornati della campagna
     * @return la campagna con i dati aggiornati
     */
    public Campaign modifyCampaign(long campaignId, long activityId, CampaignRequest campaignRequest) {
        checkValidCampaignForActivity(campaignId, activityId);
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        if (campaignRequest.end() != null
                && campaign.getEndDate() != null
                && campaignRequest.end().isAfter(campaign.getEndDate()))
            campaign.setEndDate(campaignRequest.end());
        return campaign;
    }

    /**
     * Applica le regole definite nella campagna
     *
     * @param campaignId id della campagna
     * @param order      ordine del cliente
     * @return lista di resoconti dell'applicazione delle regole
     */
    public List<String> applyRules(long campaignId, long activityId, CustomerOrder order) {
        checkValidCampaignForActivity(campaignId, activityId);
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        return abstractRuleRepository.findAll()
                .stream()
                .filter(abstractRule -> abstractRule.getCampaign()
                        .equals(campaign))
                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.apply(order))
                .toList();
    }

    /**
     * Restituisce le campagne attualmente attive di un'attività
     *
     * @param activityId id dell'attività
     * @return la lista delle campagne attive
     */
    public List<Campaign> getActiveCampaigns(long activityId) {
        return campaignRepository.findByCard_Activities_Id(activityId)
                .stream()
                .filter(campaign -> !campaign.isCurrentlyActive())
                .toList();
    }

    /**
     * Mostra i vantaggi che può portare un ordine senza applicarli
     *
     * @return la lista di vantaggi che può portare
     */
    public List<String> seeBonuses(long campaignId, CustomerOrder order) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        return abstractRuleRepository.findAll()
                .stream()
                .filter(abstractRule -> abstractRule.getCampaign()
                        .equals(campaign))
                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.seeBonus(order))
                .toList();
    }

    /**
     * Verifica se l'attività ha il permesso di accedere alla campagna
     *
     * @param campaignId id della campagna
     * @param activityId id dell'attività
     */
    private void checkValidCampaignForActivity(long campaignId, long activityId) {
        if (campaignRepository.findByCard_Activities_Id(activityId)
                .stream()
                .anyMatch(campaign -> campaign.getId() != campaignId))
            throw new RuntimeException("Attività non autorizzata a eseguire operazioni sulla campagna");
    }
}
