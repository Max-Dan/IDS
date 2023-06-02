package it.unicam.cs.ids.lp.marketing.personalizedmodels;

import it.unicam.cs.ids.lp.client.coupon.Coupon;
import it.unicam.cs.ids.lp.client.coupon.CouponRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ModelServiceTest {

    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private CouponRepository couponRepository;

    @AfterEach
    public void cleanUp() {
        modelRepository.deleteAll();
        couponRepository.deleteAll();
    }

    @Test
    void createModel() {
        Coupon coupon = couponRepository.save(new Coupon());
        ModelRequest modelRequest = new ModelRequest(coupon.getId(), "model", "message");
        MessageModel messageModel = modelService.createModel(modelRequest);
        Assertions.assertTrue(modelRepository.existsById(messageModel.getId()));
        Assertions.assertEquals(modelRequest.modelName(), messageModel.getModelName());
        Assertions.assertEquals(modelRequest.messageText(), messageModel.getMessageText());
    }

    @Test
    void findModelByName() {
        Coupon coupon = couponRepository.save(new Coupon());
        ModelRequest modelRequest = new ModelRequest(coupon.getId(), "model", "message");
        MessageModel messageModel = modelService.createModel(modelRequest);
        MessageModel foundModel = modelService.findModelByName("model");
        Assertions.assertNotNull(foundModel);
        Assertions.assertEquals(messageModel.getId(), foundModel.getId());
    }

    @Test
    void deleteModel() {
        Coupon coupon = couponRepository.save(new Coupon());
        ModelRequest modelRequest = new ModelRequest(coupon.getId(), "model", "message");
        MessageModel messageModel = modelService.createModel(modelRequest);
        modelService.deleteModel(messageModel.getId());
        Assertions.assertFalse(modelRepository.existsById(messageModel.getId()));
    }
}
