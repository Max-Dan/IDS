package it.unicam.cs.ids.lp.marketing.algorithms;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.marketing.personalizedmodels.MessageModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class MarketingAlgorithm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String algorithmName;

    private LocalDate expirationDate;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> deliveryDates;  // Date in format "MM-dd"

    @ManyToOne
    @JoinColumn
    private MessageModel model;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Customer> subscribedCustomers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketingAlgorithm that)) return false;
        return id == that.id &&
                Objects.equals(algorithmName, that.algorithmName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, algorithmName);
    }

    public boolean isTodayInDeliveryDates() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        return deliveryDates.contains(today);
    }

    public void removeCustomer(Customer customer) {
        this.subscribedCustomers.remove(customer);
    }

    public void removeDeliveryDate(String deliveryDate) {
        this.deliveryDates.remove(deliveryDate);
    }
}






