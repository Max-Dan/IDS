package it.unicam.cs.ids.lp.activity.product;

import it.unicam.cs.ids.lp.activity.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ActivityRepository activityRepository;

    public Product createProduct(long activityId, ProductRequest productRequest) {
        Product product = productMapper.apply(productRequest);
        product.setActivity(activityRepository.findById(activityId).orElseThrow());
        productRepository.save(product);
        return product;
    }
}
