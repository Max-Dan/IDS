package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.util.DataValidator;
import it.unicam.cs.ids.lp.util.DataValidatorUtil;
import it.unicam.cs.ids.lp.util.Registry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityRegistrationService
        implements DataValidator<Activity>, Registry<Activity> {

    private final ActivityRepository activityRepository;
    private final DataValidatorUtil dataValidatorUtil;
    private final ActivityMapper activityMapper;


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
    public Optional<Activity> register(Activity activity) {
        Objects.requireNonNull(activity);
        if (!areRegistrationValuesValid(activity))
            return Optional.empty();
        activityRepository.save(activity);
        return Optional.of(activity);
    }

    public Optional<Activity> register(ActivityRequest activityRequest) {
        Activity activity = activityMapper.apply(activityRequest);
        return register(activity);
    }

    @Override
    public void unregister(Activity entity) {
        activityRepository.delete(entity);
    }
}
