package it.unicam.cs.ids.lp.activity.purchase;


import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignService;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.client.order.CustomerOrderMapper;
import it.unicam.cs.ids.lp.client.order.CustomerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final CustomerCardRepository customerCardRepository;
    private final CampaignService campaignService;
    private final CustomerOrderMapper customerOrderMapper;
    private final ProductRepository productRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public List<String> checkBonus(long activityId,
                                   long customerCardId,
                                   Set<Long> productIds) {
        CustomerCard customerCard = checkValidity(activityId, customerCardId);
        CustomerOrder order = customerOrderMapper.apply(new HashSet<>(productRepository.findAllById(productIds)),
                customerCard.getCustomer());

        return customerCard.getCampaigns()
                .stream()
                .filter(Campaign::isCurrentlyActive)
                .map(campaign -> campaignService.seeBonuses(campaign.getId(), activityId, order))
                .flatMap(List::stream)
                .toList();
    }

    public List<String> applyBonus(long activityId,
                                   long customerCardId,
                                   Set<Long> productIds) {
        CustomerCard customerCard = checkValidity(activityId, customerCardId);
        CustomerOrder order = customerOrderMapper.apply(new HashSet<>(productRepository.findAllById(productIds)),
                customerCard.getCustomer());
        customerOrderRepository.save(order);

        customerCard.getCampaigns()
                .stream()
                .filter(Campaign::isCurrentlyActive)
                .forEach(campaign -> campaignService.applyRules(campaign.getId(), activityId, order));
        return null;
    }

    private CustomerCard checkValidity(long activityId,
                                       long customerCardId) {
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(customerCardId);
        if (optionalCustomerCard.isEmpty())
            throw new RuntimeException("La tessera non esiste");
        CustomerCard customerCard = optionalCustomerCard.get();
        if (customerCard.getCard().getActivities().stream().anyMatch(activity -> activity.getId() != activityId))
            throw new RuntimeException("La tessera non appartiene a questa attività");
        return customerCard;
    }
}
