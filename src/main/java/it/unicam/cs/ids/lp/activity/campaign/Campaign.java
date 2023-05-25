package it.unicam.cs.ids.lp.activity.campaign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.rules.platform_rules.campaign.CampaignRule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    @JsonIgnore
    private Card card;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private Set<CampaignRule> campaignRules = new HashSet<>();

    private LocalDate startDate;

    private LocalDate endDate;

    public boolean isCurrentlyActive() {
        return endDate == null
                || (startDate.isEqual(LocalDate.now())
                || endDate.isEqual(LocalDate.now()))
                || (startDate.isBefore(LocalDate.now())
                && endDate.isAfter(LocalDate.now()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return id == campaign.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
