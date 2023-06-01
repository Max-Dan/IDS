package it.unicam.cs.ids.lp.marketing.personalizedmodels;

import it.unicam.cs.ids.lp.client.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ModelMapper implements Function<ModelRequest, MessageModel> {

    private final CouponRepository couponRepository;

    @Override
    public MessageModel apply(ModelRequest modelRequest) {
        MessageModel messageModel = new MessageModel();
        messageModel.setModelName(modelRequest.modelName());
        messageModel.setMessageText(modelRequest.messageText());
        messageModel.setCoupon(couponRepository.findById(modelRequest.couponId()).orElseThrow());
        return messageModel;
    }
}
