package it.unicam.cs.ids.lp.marketing.personalizedmodels;

import it.unicam.cs.ids.lp.client.coupon.Coupon;
import it.unicam.cs.ids.lp.client.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    private final CouponRepository couponRepository;

    public MessageModel createModel(ModelRequest request) {
        MessageModel messageModel = modelMapper.apply(request);
        Coupon coupon = couponRepository.findById(request.couponId()).orElseThrow();
        modelRepository.save(messageModel);
        return messageModel;
    }

    public void deleteModel(long modelId) {
        modelRepository.deleteById(modelId);
    }

    public MessageModel findModelById(long modelId) {
        return modelRepository.findById(modelId).orElseThrow();
    }

    public MessageModel findModelByName(String modelName) {
        return modelRepository.findByModelName(modelName).orElseThrow();
    }
}
