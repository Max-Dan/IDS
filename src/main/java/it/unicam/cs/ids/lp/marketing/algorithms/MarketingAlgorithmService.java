package it.unicam.cs.ids.lp.marketing.algorithms;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.marketing.personalizedmodels.MessageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketingAlgorithmService {

    private final MarketingAlgorithmsRepository marketingAlgorithmsRepository;
    private final MarketingAlgorithmMapper marketingAlgorithmMapper;

    public MarketingAlgorithm createAlgorithm(MarketingAlgorithmRequest request) {
        MarketingAlgorithm algorithm = marketingAlgorithmMapper.apply(request);
        return marketingAlgorithmsRepository.save(algorithm);
    }

    public void deleteAlgorithm(long id) {
        marketingAlgorithmsRepository.deleteById(id);
    }

    public MarketingAlgorithm updateAlgorithm(MarketingAlgorithmRequest request) {
        MarketingAlgorithm algorithm = marketingAlgorithmMapper.update(request);
        return marketingAlgorithmsRepository.save(algorithm);
    }

    public MarketingAlgorithm deleteSetsAttributes(MarketingAlgorithmRequest request) {
        MarketingAlgorithm algorithm = marketingAlgorithmMapper.deleteSetsAttributes(request);
        return marketingAlgorithmsRepository.save(algorithm);
    }


    public void processAlgorithms() {
        List<MarketingAlgorithm> marketingAlgorithms = marketingAlgorithmsRepository.findAll();

        for (MarketingAlgorithm algorithm : marketingAlgorithms) {
            if (algorithm.getExpirationDate().isAfter(LocalDate.now()) && algorithm.isTodayInDeliveryDates()) {
                for (Customer customer : algorithm.getSubscribedCustomers()) {
                    MessageModel model = algorithm.getModel();
                    String message = "Dear " + customer.getName() + " "
                            //.append(customer.getSurname()).append("\n")
                            + model.getMessageText() + "\n"
                            + "Here is your coupon code: " + model.getCoupon().getId() + "\n\n";
                    System.out.println(message);
                }
            }
        }
    }
}

