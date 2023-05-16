package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/{customerId}/getCoupon/{couponId}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable long couponId, @PathVariable long customerId) {
        return ResponseEntity.ok(couponService.getCoupon(customerId, couponId).orElseThrow());
    }

    @GetMapping("/{customerId}/getCoupons")
    public ResponseEntity<Set<Coupon>> getCoupons(@PathVariable long customerId) {
        return ResponseEntity.ok(couponService.getCoupons(customerId));
    }

    @GetMapping("/{customerCardId}/getCardCoupons")
    public ResponseEntity<Set<Coupon>> getCardCoupons(@PathVariable long customerCardId) {
        return ResponseEntity.ok(couponService.getCardCoupons(customerCardId));
    }

    @GetMapping("/isCouponValid/{couponId}")
    public ResponseEntity<Boolean> isCouponValid(@PathVariable long couponId) {
        return ResponseEntity.ok(couponService.isCouponValid(couponId));
    }

    @GetMapping("/applyCoupons")
    public ResponseEntity<List<String>> applyCoupons(@RequestBody Set<Long> couponIds, CustomerOrder order) {
        return ResponseEntity.ok(couponService.applyCoupons(couponIds, order));
    }

    @PutMapping("/{customerId}/giveCoupon")
    public ResponseEntity<?> giveCoupon(@PathVariable long customerId, @RequestBody CouponRequest couponRequest) {
        couponService.createCoupon(customerId, couponRequest);
        return ResponseEntity.ok("");
    }

}
