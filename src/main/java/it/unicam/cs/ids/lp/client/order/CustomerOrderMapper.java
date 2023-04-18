package it.unicam.cs.ids.lp.client.order;

import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.client.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.BiFunction;

@Service
public class CustomerOrderMapper implements BiFunction<Set<Product>, Customer, CustomerOrder> {

    @Override
    public CustomerOrder apply(Set<Product> products, Customer customer) {
        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setProducts(products);
        order.setDate(LocalDate.now());
        return order;
    }
}
