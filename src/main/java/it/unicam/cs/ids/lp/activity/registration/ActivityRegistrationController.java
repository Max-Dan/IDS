package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
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

    @Autowired
    private ActivityRepository activityRepository;

    @PutMapping("/register")
    public ResponseEntity<?> registerActivity(@RequestBody ActivityRequest activityRequest) {
        Activity activity = activityMapper.apply(activityRequest);
        boolean registered = activityRegistrationService.register(activity);
        if (registered) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/unregister/{activityId}")
    public ResponseEntity<?> unregisterActivity(@PathVariable long activityId) {
        activityRegistrationService.unregister(activityRepository.findById(activityId).orElseThrow());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
