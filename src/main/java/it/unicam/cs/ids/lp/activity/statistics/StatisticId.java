package it.unicam.cs.ids.lp.activity.statistics;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ContentCategory;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class StatisticId implements Serializable {
    private Activity activity;
    private ContentCategory category;
    private LocalDate date;
}
