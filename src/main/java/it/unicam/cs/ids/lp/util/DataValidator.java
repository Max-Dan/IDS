package it.unicam.cs.ids.lp.util;

import org.springframework.stereotype.Component;

@Component
public interface DataValidator<T> {

    boolean areRegistrationValuesValid(T item);
}
