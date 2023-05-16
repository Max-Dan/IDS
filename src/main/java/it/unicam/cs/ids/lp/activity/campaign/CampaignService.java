package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.platform_rules.campaign.CampaignRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CardRepository cardRepository;
    private final CampaignMapper campaignMapper;
    private final CampaignRepository campaignRepository;
    private final CampaignRuleRepository campaignRuleRepository;


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
     * Termina la validità della campagna
     *
     * @param campaignId id della campagna
     * @param activityId id dell'attività
     */
    public void terminateCampaign(long campaignId, long activityId) {
        checkValidCampaignForActivity(campaignId, activityId);
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        campaign.setEndDate(LocalDate.now());
        campaignRepository.save(campaign);
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
        if (campaignRequest.name() != null)
            campaign.setName(campaignRequest.name());
        if (campaignRequest.end() != null
                && campaign.getEndDate() != null
                && campaignRequest.end().isAfter(campaign.getEndDate()))
            campaign.setEndDate(campaignRequest.end());
        campaignRepository.save(campaign);
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
        return campaignRuleRepository.findByCampaign_Id(campaignId)
                .stream()
                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.getRule().applyRule(order))
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
                .filter(Campaign::isCurrentlyActive)
                .toList();
    }

    /**
     * Restituisce le campagne attive e non
     *
     * @param activityId id dell'attività
     * @return lista delle campagne
     */
    public List<Campaign> getAllCampaigns(long activityId) {
        return campaignRepository.findByCard_Activities_Id(activityId)
                .stream()
                .sorted(Comparator.comparing(Campaign::isCurrentlyActive))
                .toList();
    }

    /**
     * Mostra i vantaggi che può portare un ordine senza applicarli
     *
     * @return la lista di vantaggi che può portare
     */
    public List<String> seeBonuses(long campaignId, CustomerOrder order) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        return campaignRuleRepository.findAll()
                .stream()
                .filter(abstractRule -> abstractRule.getCampaign()
                        .equals(campaign))
                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.getRule().seeBonus(order))
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
                .noneMatch(campaign -> campaign.getId() == campaignId))
            throw new RuntimeException("Attività non autorizzata a eseguire operazioni sulla campagna");
    }
}
