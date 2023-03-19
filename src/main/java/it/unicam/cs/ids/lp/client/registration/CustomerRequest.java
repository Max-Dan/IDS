package it.unicam.cs.ids.lp.client.registration;

record CustomerRequest(String name,
                       String surname,
                       String telephoneNumber,
                       String email,
                       String password,
                       String referralCode) {
}
