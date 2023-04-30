package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.client.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    private LocalDate end;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
