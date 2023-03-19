package it.unicam.cs.ids.lp.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String surname;
    private String telephoneNumber;
    private String email;
    @JsonIgnore
    private String password;
    private LocalDate registrationDate;
    @OneToMany
    private Set<CustomerCard> cards;
    private String referred;
    private String referralCode;
    private int referredTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

