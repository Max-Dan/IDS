package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.util.DataValidator;
import it.unicam.cs.ids.lp.util.DataValidatorUtil;
import it.unicam.cs.ids.lp.util.Registry;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ActivityRegistrationService
        implements DataValidator<Activity>, Registry<Activity> {

    private final ActivityRepository activityRepository;

    private final DataValidatorUtil dataValidatorUtil;

    public ActivityRegistrationService(ActivityRepository activityRepository,
                                       DataValidatorUtil dataValidatorUtil) {
        this.activityRepository = activityRepository;
        this.dataValidatorUtil = dataValidatorUtil;
    }


    @Override
    public boolean areRegistrationValuesValid(Activity activity) {
        return activity != null
                && dataValidatorUtil.isNameValid(activity.getName())
                && dataValidatorUtil.isAddressValid(activity.getAddress())
                && dataValidatorUtil.isTelephoneNumberValid(activity.getTelephoneNumber())
                && dataValidatorUtil.isEmailValid(activity.getEmail())
                && dataValidatorUtil.isCategoryValid(activity.getCategory());
    }

    @Override
    public boolean register(Activity activity) {
        Objects.requireNonNull(activity);
        if (!areRegistrationValuesValid(activity))
            return false;
        activityRepository.save(activity);
        return true;
    }

    @Override
    public void unregister(Activity entity) {
        activityRepository.delete(entity);
    }
}
