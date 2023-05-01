package it.unicam.cs.ids.lp.activity.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest) {
        Product product = productMapper.apply(productRequest);
        productRepository.save(product);
        return product;
    }
}
