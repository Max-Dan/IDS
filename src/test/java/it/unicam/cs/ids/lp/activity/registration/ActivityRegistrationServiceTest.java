package it.unicam.cs.ids.lp.activity.registration;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ContentCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ActivityRegistrationServiceTest {

    @Autowired
    private ActivityRegistrationService activityRegistrationService;
    @Autowired
    private ActivityMapper activityMapper;

    @Test
    void registerActivity() {
        assertThrows(NullPointerException.class,
                () -> activityRegistrationService.register(null));
        Activity activity = activityMapper.apply(new ActivityRequest(
                "Apple",
                "via california",
                "445-678-9034",
                "test@gmail.com",
                ContentCategory.TECHNOLOGY,
                "sonoLaApple"
        ));
        Assertions.assertTrue(activityRegistrationService.register(activity));
    }
}
