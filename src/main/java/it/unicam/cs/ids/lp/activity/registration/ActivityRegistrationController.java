package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityRegistrationController {

    private final ActivityMapper activityMapper;

    private final ActivityRegistrationService activityRegistrationService;

    private final ActivityRepository activityRepository;

    @PutMapping("/register")
    public ResponseEntity<?> registerActivity(@RequestBody ActivityRequest activityRequest) {
        Activity activity = activityMapper.apply(activityRequest);
        return activityRegistrationService.register(activity)
                .map(activity1 -> new ResponseEntity<>(HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/unregister/{activityId}")
    public ResponseEntity<?> unregisterActivity(@PathVariable long activityId) {
        activityRegistrationService.unregister(activityRepository.findById(activityId).orElseThrow());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
