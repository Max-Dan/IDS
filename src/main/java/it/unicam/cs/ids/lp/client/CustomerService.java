package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.client.registration.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CampaignRepository campaignRepository;
    private final CustomerCardRepository customerCardRepository;

    public void modifyCustomerData(long customerId, CustomerRequest customerRequest) {
        Customer customer = customerRepository.getReferenceById(customerId);
        if (customerRequest.name() != null)
            customer.setName(customerRequest.name());
        if (customerRequest.surname() != null)
            customer.setSurname(customerRequest.surname());
        if (customerRequest.telephoneNumber() != null)
            customer.setTelephoneNumber(customerRequest.telephoneNumber());
        if (customerRequest.email() != null)
            customer.setEmail(customerRequest.email());
    }

    /**
     * Iscrive il cliente a una campagna
     *
     * @param customerId id del customer
     * @param campaignId id della campagna
     * @return la campagna iscritta dal customer
     */
    public Optional<Campaign> subscribeToCampaign(long customerId, long campaignId) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(campaignId);
        if (optionalCampaign.isEmpty())
            return Optional.empty();
        Campaign campaign = optionalCampaign.get();
        CustomerCard customerCard = customerRepository.findById(customerId).orElseThrow()
                .getCards()
                .stream()
                .filter(customerCard1 -> customerCard1.getCard().equals(campaign.getCard()))
                .findFirst().orElse(null);
        if (customerCard == null || customerCard.getCampaigns().contains(campaign))
            return Optional.empty();
        customerCard.getCampaigns().add(campaign);
        customerCardRepository.save(customerCard);
        return Optional.of(campaign);
    }
}
