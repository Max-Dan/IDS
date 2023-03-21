package it.unicam.cs.ids.lp.client.order;

import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.client.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Order {
    @Id
    @Column(nullable = false)
    private long id;
    @ManyToMany
    @JoinTable
    private List<Product> products;
    @ManyToOne
    @JoinColumn
    private Customer customer;

}
