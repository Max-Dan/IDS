package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.client.card.CustomerCard;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer {
    @Id
    private String name;
    private String surname;
    private String telephoneNumber;
    private String email;
    @OneToOne(mappedBy = "customer")
    private CustomerAccount customerAccount;
    @OneToMany(mappedBy = "id")
    private Set<CustomerCard> cards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return name != null && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

