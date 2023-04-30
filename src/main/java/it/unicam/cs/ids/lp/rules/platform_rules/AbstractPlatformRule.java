package it.unicam.cs.ids.lp.rules.platform_rules;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class AbstractPlatformRule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
}
