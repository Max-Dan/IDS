package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/activityRegistration")
public class ActivityRegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerActivity(@RequestBody ActivityRequest activityRequest) {
        Activity activity = setActivity(activityRequest);
        ActivityAccount activityAccount = setActivityProfile(activityRequest);
        boolean registered = activityRegistrationService.registerActivity(activity, activityAccount);
        if (registered) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    private ActivityAccount setActivityProfile(ActivityRequest activityRequest) {
        ActivityAccount activityAccount = new ActivityAccount();
        activityAccount.setName(activityRequest.name());
        activityAccount.setPassword(passwordEncoder.encode(activityRequest.password()));
        activityAccount.setRegistrationDate(LocalDate.now());
        return activityAccount;
    }

    private Activity setActivity(ActivityRequest activityRequest) {
        Activity activity = new Activity();
        activity.setName(activityRequest.name());
        activity.setAddress(activityRequest.address());
        activity.setTelephoneNumber(activityRequest.telephoneNumber());
        activity.setEmail(activityRequest.email());
        activity.setCategory(activityRequest.category());
        return activity;
    }

    protected record ActivityRequest(String name, String address, String telephoneNumber, String email,
                                     Activity.ContentCategory category, String password) {
    }
}
