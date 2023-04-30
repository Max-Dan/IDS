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

    @GetMapping("/isCouponValid/{couponId}")
    public ResponseEntity<Boolean> isCouponValid(@PathVariable long couponId) {
        return ResponseEntity.ok(couponService.isCouponValid(couponId));
    }

    @GetMapping("/applyCoupons")
    public ResponseEntity<List<String>> applyCoupons(@RequestBody Set<Long> couponIds, CustomerOrder order) {
        return ResponseEntity.ok(couponService.applyCoupons(couponIds, order));
    }
}