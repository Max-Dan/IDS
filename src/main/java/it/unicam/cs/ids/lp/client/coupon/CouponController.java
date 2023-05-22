package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/{customerCardId}/getCoupon/{couponId}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable long couponId, @PathVariable long customerCardId) {
        return ResponseEntity.ok(couponService.getCoupon(customerCardId, couponId).orElseThrow());
    }

    @GetMapping("/{customerCardId}/getCoupons")
    public ResponseEntity<Set<Coupon>> getCardCoupons(@PathVariable long customerCardId) {
        return ResponseEntity.ok(couponService.getCardCoupons(customerCardId));
    }

    @GetMapping("/isCouponValid/{couponId}")
    public ResponseEntity<Boolean> isCouponValid(@PathVariable long couponId) {
        return ResponseEntity.ok(couponService.isCouponValid(couponId));
    }

    @GetMapping("/applyCoupons")
    public ResponseEntity<String> applyCoupons(@RequestBody Set<Long> couponIds, CustomerOrder order) {
        couponService.applyCoupons(couponIds, order);
        return ResponseEntity.ok("");
    }

    @PutMapping("/{customerCardId}/giveCoupon")
    public ResponseEntity<?> giveCoupon(@PathVariable long customerCardId, @RequestBody CouponRequest couponRequest) {
        couponService.createCoupon(customerCardId, couponRequest);
        return ResponseEntity.ok("");
    }

}
