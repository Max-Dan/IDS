package it.unicam.cs.ids.lp.card;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CustomerCard {
    @Id
    private String identificator;
    private String telephoneNumber;
    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerCard.CardProgram program;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerCard customerCard = (CustomerCard) o;
        return identificator != null && Objects.equals(identificator, customerCard.identificator);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum CardProgram {
        Points,
        Levels,
        Membership,
        Cashback,
        Coalition,
    }
}

