package it.unicam.cs.ids.lp.activity.product;

import it.unicam.cs.ids.lp.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductMapper implements Function<ProductRequest, Product> {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Product apply(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setActivities(activityRepository.findAllById(productRequest.activitiesIds()));
        product.setPrice(productRequest.price());
        return product;
    }
}
