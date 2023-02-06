package it.unicam.cs.ids.lp.activity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Activity {
    @Id
    private String name;
    private String address;
    private String telephoneNumber;
    private String email;
    @ManyToOne
    private List<ContentCategory> category;
    private LocalDateTime registrationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Activity activity = (Activity) o;
        return name != null && Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
