package it.unicam.cs.ids.lp.activity.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductMapper implements Function<ProductRequest, Product> {

    @Override
    public Product apply(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        return product;
    }
}
