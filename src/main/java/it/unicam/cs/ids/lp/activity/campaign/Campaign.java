package it.unicam.cs.ids.lp.activity.campaign;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "campaign_id")
    @ToString.Exclude
    private List<Campaign> pastCampaigns;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "active_campaign_id", referencedColumnName = "id")
    private Campaign activeCampaign;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Campaign campaign = (Campaign) o;
        return getId() != 0 && Objects.equals(getId(), campaign.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
