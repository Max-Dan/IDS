package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.client.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CustomerCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ManyToOne
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private CardProgram program;
    private Integer points = 0;
    private Integer tier = 1;
    private Boolean family = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCard that = (CustomerCard) o;
        return customer.equals(that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer);
    }
}

