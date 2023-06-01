package it.unicam.cs.ids.lp.marketing.personalizedmodels;

import it.unicam.cs.ids.lp.client.coupon.Coupon;
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
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String modelName;

    private String messageText;

    @ManyToOne
    @JoinColumn
    private Coupon coupon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageModel that)) return false;
        return id == that.id &&
                Objects.equals(modelName, that.modelName) &&
                Objects.equals(messageText, that.messageText) &&
                Objects.equals(coupon, that.coupon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modelName);
    }
}


