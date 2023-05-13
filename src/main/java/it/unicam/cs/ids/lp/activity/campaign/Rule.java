package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.client.order.CustomerOrder;

import java.util.function.Function;

public interface Rule<R> extends Function<CustomerOrder, R> {

    R seeBonus(CustomerOrder order);
}
