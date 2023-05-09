package it.unicam.cs.ids.lp.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.lp.activity.card.Card;
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
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String name;

    private String address;

    private String telephoneNumber;

    private String email;

    @JsonIgnore
    private String password;

    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    private ContentCategory category;

    @ManyToOne
    @JoinColumn
    private Card card;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return id == activity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
