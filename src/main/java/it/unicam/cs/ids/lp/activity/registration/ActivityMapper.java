package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ActivityMapper implements Function<ActivityRequest, Activity> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Activity apply(ActivityRequest activityRequest) {
        Activity activity = new Activity();
        activity.setName(activityRequest.name());
        activity.setAddress(activityRequest.address());
        activity.setTelephoneNumber(activityRequest.telephoneNumber());
        activity.setEmail(activityRequest.email());
        activity.setPassword(passwordEncoder.encode(activityRequest.password()));
        activity.setRegistrationDate(LocalDate.now());
        activity.setCategory(activityRequest.category());
        return activity;
    }
}
