package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityRegistrationController {

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @PutMapping("/activityRegistration/register")
    public ResponseEntity<?> registerActivity(@RequestBody ActivityRequest activityRequest) {
        Activity activity = activityMapper.apply(activityRequest);
        boolean registered = activityRegistrationService.registerActivity(activity);
        if (registered) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/activityUnregistration/{name}")
    public ResponseEntity<?> unregisterActivity(@PathVariable String name) {
        activityRegistrationService.unregisterActivityByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
