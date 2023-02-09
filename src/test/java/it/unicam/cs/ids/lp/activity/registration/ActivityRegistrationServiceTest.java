package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ActivityRegistrationServiceTest {

    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @Test
    void registerActivity() {
        assertThrows(NullPointerException.class,
                () -> activityRegistrationService.registerActivity(null, new ActivityAccount()));
        Activity activity = new Activity();
        activity.setName("Apple");
        activity.setAddress("via california");
        activity.setEmail("test@gmail.com");
        activity.setTelephoneNumber("445-678-9034");
        activity.setCategory(Activity.ContentCategory.TECH);

        ActivityAccount activityAccount = new ActivityAccount();
        activityAccount.setName(activity.getName());
        activityAccount.setPassword("sonoLaApple");
        Assertions.assertTrue(activityRegistrationService.registerActivity(activity, activityAccount));
    }

    @Test
    void isNameValid() {

    }

    @Test
    void isAddressValid() {
    }

    @Test
    void isTelephoneNumberValid() {
    }

    @Test
    void isEmailValid() {
    }
}