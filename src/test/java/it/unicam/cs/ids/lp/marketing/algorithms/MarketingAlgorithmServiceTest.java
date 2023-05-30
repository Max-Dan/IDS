package it.unicam.cs.ids.lp.marketing.algorithms;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.coupon.Coupon;
import it.unicam.cs.ids.lp.client.coupon.CouponRepository;
import it.unicam.cs.ids.lp.marketing.personalizedmodels.MessageModel;
import it.unicam.cs.ids.lp.marketing.personalizedmodels.ModelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class MarketingAlgorithmServiceTest {

    @Autowired
    private MarketingAlgorithmService marketingAlgorithmService;

    @Autowired
    private MarketingAlgorithmsRepository marketingAlgorithmsRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MarketingAlgorithmMapper marketingAlgorithmMapper;
    @Autowired
    private CouponRepository couponRepository;

    @AfterEach
    public void cleanUp() {
        marketingAlgorithmsRepository.deleteAll();
        modelRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void createAlgorithm() {
        MessageModel model = modelRepository.save(new MessageModel());
        Customer customer = customerRepository.save(new Customer());

        Set<String> deliveryDates = new HashSet<>();
        deliveryDates.add("05-30");

        Set<Long> subscribedCustomerIds = new HashSet<>();
        subscribedCustomerIds.add(customer.getId());

        MarketingAlgorithmRequest request = new MarketingAlgorithmRequest(
                0L, "algorithm", "2024-05-30", deliveryDates, model.getId(), subscribedCustomerIds
        );

        MarketingAlgorithm algorithm = marketingAlgorithmService.createAlgorithm(request);

        Assertions.assertTrue(marketingAlgorithmsRepository.existsById(algorithm.getId()));
        Assertions.assertEquals(request.algorithmName(), algorithm.getAlgorithmName());
    }

    @Test
    void deleteAlgorithm() {
        MessageModel model = modelRepository.save(new MessageModel());
        Customer customer = customerRepository.save(new Customer());

        Set<String> deliveryDates = new HashSet<>();
        deliveryDates.add("05-30");

        Set<Long> subscribedCustomerIds = new HashSet<>();
        subscribedCustomerIds.add(customer.getId());

        MarketingAlgorithmRequest request = new MarketingAlgorithmRequest(
                0L, "algorithm", "2024-05-30", deliveryDates, model.getId(), subscribedCustomerIds
        );

        MarketingAlgorithm algorithm = marketingAlgorithmService.createAlgorithm(request);
        marketingAlgorithmService.deleteAlgorithm(algorithm.getId());

        Assertions.assertFalse(marketingAlgorithmsRepository.existsById(algorithm.getId()));
    }

    @Test
    void updateAlgorithm() {
        MessageModel model = modelRepository.save(new MessageModel());
        Customer customer = customerRepository.save(new Customer());

        Set<String> deliveryDates = new HashSet<>();
        deliveryDates.add("05-30");

        Set<Long> subscribedCustomerIds = new HashSet<>();
        subscribedCustomerIds.add(customer.getId());

        MarketingAlgorithmRequest request = new MarketingAlgorithmRequest(
                0L, "algorithm", "2024-05-30", deliveryDates, model.getId(), subscribedCustomerIds
        );

        MarketingAlgorithm algorithm = marketingAlgorithmService.createAlgorithm(request);

        // Updating the algorithm's name
        MarketingAlgorithmRequest updateRequest = new MarketingAlgorithmRequest(
                algorithm.getId(), "updatedAlgorithm", "2024-05-30", deliveryDates, model.getId(), subscribedCustomerIds
        );

        MarketingAlgorithm updatedAlgorithm = marketingAlgorithmService.updateAlgorithm(updateRequest);

        Assertions.assertEquals("updatedAlgorithm", updatedAlgorithm.getAlgorithmName());
    }

    @Test
    void deleteSetsAttributes() {
        MessageModel model = modelRepository.save(new MessageModel());
        Customer customer = customerRepository.save(new Customer());

        Set<String> deliveryDates = new HashSet<>();
        deliveryDates.add("05-30");

        Set<Long> subscribedCustomerIds = new HashSet<>();
        subscribedCustomerIds.add(customer.getId());

        MarketingAlgorithmRequest request = new MarketingAlgorithmRequest(
                0L, "algorithm", "2024-05-30", deliveryDates, model.getId(), subscribedCustomerIds
        );

        MarketingAlgorithm algorithm = marketingAlgorithmService.createAlgorithm(request);


        MarketingAlgorithmRequest deleteRequest = new MarketingAlgorithmRequest(
                algorithm.getId(), "algorithm", "2024-05-30", new HashSet<>(), model.getId(), new HashSet<>()
        );

        MarketingAlgorithm updatedAlgorithm = marketingAlgorithmService.deleteSetsAttributes(deleteRequest);

        Assertions.assertTrue(updatedAlgorithm.getDeliveryDates().isEmpty());
        Assertions.assertTrue(updatedAlgorithm.getSubscribedCustomers().isEmpty());
    }

    @Test
    void processAlgorithms() {
        MessageModel model = modelRepository.save(new MessageModel());
        Customer customer1 = customerRepository.save(new Customer());
        Customer customer2 = customerRepository.save(new Customer());

        model.setCoupon(couponRepository.save(new Coupon()));
        model.setMessageText("We have a coupon for you");
        modelRepository.save(model);
        customer1.setName("Stone");
        customerRepository.save(customer1);
        customer2.setName("Amber");
        customerRepository.save(customer2);

        Set<String> deliveryDates = new HashSet<>();
        deliveryDates.add(LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd")));

        Set<Long> subscribedCustomerIds = new HashSet<>();
        subscribedCustomerIds.add(customer1.getId());
        subscribedCustomerIds.add(customer2.getId());

        MarketingAlgorithmRequest request = new MarketingAlgorithmRequest(
                0L, "algorithm", LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE), deliveryDates, model.getId(), subscribedCustomerIds
        );

        marketingAlgorithmService.createAlgorithm(request);

        Assertions.assertDoesNotThrow(() -> marketingAlgorithmService.processAlgorithms());
    }


}

