package it.unicam.cs.ids.lp.activity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ActivityAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String password;
    private LocalDate registrationDate;
    @OneToOne(mappedBy = "activityAccount")
    private Activity activity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityAccount that = (ActivityAccount) o;
        return activity.equals(that.activity) && password.equals(that.password) && registrationDate.equals(that.registrationDate);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
