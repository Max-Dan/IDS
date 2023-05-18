package it.unicam.cs.ids.lp.client.card.programs;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class CashbackData extends ProgramData {

    private int remainingCashback;


}
