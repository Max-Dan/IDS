package it.unicam.cs.ids.lp.activity;


import it.unicam.cs.ids.lp.activity.registration.ActivityRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/{activityId}/getActivityData")
    public ResponseEntity<?> getActivityData(@PathVariable long activityId) {
        Optional<Activity> newActivity = activityService.getActivityData(activityId);
        return newActivity.isPresent() ? ResponseEntity.ok().body(newActivity.get())
                : ResponseEntity.badRequest().body("Id non corretto");
    }

    @PostMapping("/{activityId}/modifyData")
    public ResponseEntity<?> modifyActivityData(@PathVariable long activityId,
                                                @RequestBody ActivityRequest request) {
        Optional<Activity> newActivity = activityService.modifyActivityData(activityId, request);
        return newActivity.isPresent() ? ResponseEntity.ok().body(newActivity.get())
                : ResponseEntity.badRequest().body("Dati inseriti non corretti");
    }
}
