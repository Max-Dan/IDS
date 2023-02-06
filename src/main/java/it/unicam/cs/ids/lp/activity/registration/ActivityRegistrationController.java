package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityRegistrationController {

    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @PostMapping("/register")
    public void registerActivity(@RequestBody Activity activity) {
        activityRegistrationService.registerActivity(activity);
    }
}
