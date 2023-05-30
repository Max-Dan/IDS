package it.unicam.cs.ids.lp.activity.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PutMapping("/{activityId}/createProduct")
    public ResponseEntity<?> createProduct(@PathVariable long activityId,
                                           @RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(activityId, productRequest);
        return ResponseEntity.ok(product);
    }
}
