package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.ContentCategory;
import it.unicam.cs.ids.lp.activity.card.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    private Card activityCard;
    private String description;
    private String shopUrl;
    @Enumerated(EnumType.STRING)
    private ContentCategory category;
    @Transient
    private List<Rule<?>> rules;
    private LocalDate start;
    private LocalDate end;
}
