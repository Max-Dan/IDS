package it.unicam.cs.ids.lp.marketing.algorithms;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.marketing.personalizedmodels.MessageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/MarketingAutomation")
@RequiredArgsConstructor
public class MarketingAlgorithmController {

    private final MarketingAlgorithmsRepository marketingAlgorithmsRepository;

    @GetMapping
    public ResponseEntity<String> processAlgorithms() {
        List<MarketingAlgorithm> marketingAlgorithms = marketingAlgorithmsRepository.findAll();

        StringBuilder result = new StringBuilder();

        for (MarketingAlgorithm algorithm : marketingAlgorithms) {
            if (algorithm.getExpirationDate().isAfter(LocalDate.now()) && algorithm.isTodayInDeliveryDates()) {
                for (Customer customer : algorithm.getSubscribedCustomers()) {
                    MessageModel model = algorithm.getModel();
                    result.append("Customer Name: ").append(customer.getName()).append(" ")
                            .append(customer.getSurname()).append("\n")
                            .append("Message: ").append(model.getMessageText()).append("\n")
                            .append("Here is the coupon code: ").append(model.getCoupon().getId()).append("\n\n");
                }
            }
        }

        return ResponseEntity.ok(result.toString());
    }

}

