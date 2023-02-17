package it.unicam.cs.ids.lp.card.client;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        it.unicam.cs.ids.lp.card.client.Customer customer = (it.unicam.cs.ids.lp.card.client.Customer) o;
        return name != null && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

