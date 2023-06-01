package it.unicam.cs.ids.lp.marketing.algorithms;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.marketing.personalizedmodels.MessageModel;
import it.unicam.cs.ids.lp.marketing.personalizedmodels.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MarketingAlgorithmMapper implements Function<MarketingAlgorithmRequest, MarketingAlgorithm> {

    private final ModelRepository modelRepository;
    private final CustomerRepository customerRepository;
    private final MarketingAlgorithmsRepository marketingAlgorithmsRepository;
    @Override
    public MarketingAlgorithm apply(MarketingAlgorithmRequest request) {
        MarketingAlgorithm marketingAlgorithm = new MarketingAlgorithm();
        marketingAlgorithm.setAlgorithmName(request.algorithmName());
        marketingAlgorithm.setExpirationDate(LocalDate.parse(request.expirationDate(), DateTimeFormatter.ISO_LOCAL_DATE));

        Set<String> uniqueDates = new HashSet<>(request.deliveryDates());
        marketingAlgorithm.setDeliveryDates(uniqueDates);

        MessageModel model = modelRepository.findById(request.messageModelId())
                .orElseThrow(() -> new RuntimeException("Model not found"));
        marketingAlgorithm.setModel(model);

        Set<Customer> customers = new HashSet<>();
        for (Long customerId : request.subscribedCustomerIds()) {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            customers.add(customer);
        }
        marketingAlgorithm.setSubscribedCustomers(customers);

        return marketingAlgorithm;
    }

    public MarketingAlgorithm update(MarketingAlgorithmRequest request) {
        MarketingAlgorithm marketingAlgorithm = marketingAlgorithmsRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Algorithm not found"));

        marketingAlgorithm.setAlgorithmName(request.algorithmName());
        marketingAlgorithm.setDeliveryDates(request.deliveryDates());
        marketingAlgorithm.setExpirationDate(LocalDate.parse(request.expirationDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        MessageModel model = modelRepository.findById(request.messageModelId())
                .orElseThrow(() -> new RuntimeException("Message Model not found"));
        marketingAlgorithm.setModel(model);

        Set<Customer> customers = request.subscribedCustomerIds().stream()
                .map(id -> customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found")))
                .collect(Collectors.toSet());
        marketingAlgorithm.setSubscribedCustomers(customers);

        marketingAlgorithmsRepository.save(marketingAlgorithm);
        return marketingAlgorithmsRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Algorithm not found"));
    }


    public MarketingAlgorithm deleteSetsAttributes(MarketingAlgorithmRequest request) {
        MarketingAlgorithm marketingAlgorithm = marketingAlgorithmsRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Algorithm not found"));

        for (Long customerId : request.subscribedCustomerIds()) {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            marketingAlgorithm.removeCustomer(customer);
        }

        for (String deliveryDate : request.deliveryDates()) {
            marketingAlgorithm.removeDeliveryDate(deliveryDate);
        }
        marketingAlgorithmsRepository.save(marketingAlgorithm);
        return marketingAlgorithmsRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Algorithm not found"));

    }

}


