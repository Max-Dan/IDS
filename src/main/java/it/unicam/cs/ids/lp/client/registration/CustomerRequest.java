package it.unicam.cs.ids.lp.client.registration;

public record CustomerRequest(String name,
                              String surname,
                              String telephoneNumber,
                              String email,
                              String password) {
}
