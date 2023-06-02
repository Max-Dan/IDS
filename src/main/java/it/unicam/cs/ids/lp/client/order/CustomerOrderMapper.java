package it.unicam.cs.ids.lp.client.order;

import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.client.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerOrderMapper {

    public CustomerOrder mapOrder(List<Product> products, Customer customer) {
        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setProducts(products);
        order.setDate(LocalDate.now());
        return order;
    }
}
