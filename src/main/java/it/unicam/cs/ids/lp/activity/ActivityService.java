package it.unicam.cs.ids.lp.activity;

import it.unicam.cs.ids.lp.activity.registration.ActivityRequest;
import it.unicam.cs.ids.lp.util.DataValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    private final DataValidator<Activity> dataValidator;

    public ActivityService(ActivityRepository activityRepository,
                           DataValidator<Activity> dataValidator) {
        this.activityRepository = activityRepository;
        this.dataValidator = dataValidator;
    }


    public Optional<Activity> getActivityData(long activityId) {
        return activityRepository.findById(activityId);
    }

    public Optional<Activity> modifyActivityData(long activityId, ActivityRequest request) {
        Activity activity = activityRepository.findById(activityId).orElseThrow();
        if (request.name() != null)
            activity.setName(request.name());
        if (request.address() != null)
            activity.setAddress(request.address());
        if (request.telephoneNumber() != null)
            activity.setTelephoneNumber(request.telephoneNumber());
        if (request.email() != null)
            activity.setEmail(request.email());
        if (request.category() != null)
            activity.setCategory(request.category());

        if (!dataValidator.areRegistrationValuesValid(activity))
            return Optional.empty();
        activityRepository.save(activity);
        return Optional.of(activity);
    }
}
