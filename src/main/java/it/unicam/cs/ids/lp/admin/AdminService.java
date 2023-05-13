package it.unicam.cs.ids.lp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public boolean registerAdmin(Admin admin) {
        Objects.requireNonNull(admin);
        if (adminRepository.existsByEmail(admin.getEmail())
                || !isEmailValid(admin.getEmail())) {
            return false;
        }

        adminRepository.save(admin);
        return true;
    }

    /**
     * Verifica che l'email sia valida
     *
     * @param email l'email
     * @return true se l'email è valida, false altrimenti
     */
    protected boolean isEmailValid(String email) {
        return email == null
                || email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public void unregisterAdmin(long adminId) {
        adminRepository.deleteById(adminId);
    }
}
