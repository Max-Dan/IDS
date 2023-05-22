package it.unicam.cs.ids.lp.activity.registration;

public record ActivityRequest(String name,
                              String address,
                              String telephoneNumber,
                              String email,
                              String password) {
}
