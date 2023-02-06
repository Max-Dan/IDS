package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/activityRegistration")
public class ActivityRegistrationController {

    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerActivity(@RequestBody ActivityRequest activityRequest) {
        Activity activity = new Activity();
        activity.setName(activityRequest.name());
        activity.setAddress(activityRequest.address());
        activity.setTelephoneNumber(activityRequest.telephoneNumber());
        activity.setEmail(activityRequest.email());
        activity.setCategory(activityRequest.category());
        activity.setRegistrationDate(LocalDate.now());
        boolean registered = activityRegistrationService.registerActivity(activity);
        if (registered) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    private record ActivityRequest(String name, String address, String telephoneNumber, String email,
                                   ContentCategory category) {
    }
}
