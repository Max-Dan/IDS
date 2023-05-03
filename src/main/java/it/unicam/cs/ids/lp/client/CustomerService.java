package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.client.registration.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CampaignRepository campaignRepository;

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
     * @return true se ha i requisiti per iscriversi alla campagna, false altrimenti
     */
    public boolean subscribeToCampaign(long customerId, long campaignId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        if (customer.getCards()
                .stream()
                .noneMatch(customerCard -> customerCard.getCard().equals(campaign.getCard())))
            return false;
        customer.getCurrentlySubscribedCampaigns().add(campaign);
        customerRepository.save(customer);
        return true;
    }
}
