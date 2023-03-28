package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.ContentCategory;

public record ActivityRequest(String name,
                              String address,
                              String telephoneNumber,
                              String email,
                              ContentCategory category,
                              String password) {
}
