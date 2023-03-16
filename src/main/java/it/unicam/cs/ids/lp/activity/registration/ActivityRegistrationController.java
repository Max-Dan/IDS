package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
public class ActivityRegistrationController {

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @PutMapping("/register")
    public ResponseEntity<?> registerActivity(@RequestBody ActivityRequest activityRequest) {
        Activity activity = activityMapper.apply(activityRequest);
        boolean registered = activityRegistrationService.registerActivity(activity);
        if (registered) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/unregister/{activityId}")
    public ResponseEntity<?> unregisterActivity(@PathVariable long activityId) {
        activityRegistrationService.unregisterActivityById(activityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
