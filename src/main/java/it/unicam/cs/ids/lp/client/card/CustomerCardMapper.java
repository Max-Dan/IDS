package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerCardMapper implements Function<CustomerCardRequest, CustomerCard> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerCard apply(CustomerCardRequest customerCardRequest) {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setCustomer(customerRepository.findById(customerCardRequest.customerId()).orElseThrow());
        return customerCard;
    }
}
