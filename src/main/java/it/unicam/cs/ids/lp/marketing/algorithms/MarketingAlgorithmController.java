package it.unicam.cs.ids.lp.marketing.algorithms;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/MarketingAutomation")
@RequiredArgsConstructor
public class MarketingAlgorithmController {

    private final MarketingAlgorithmService marketingAlgorithmService;

    @PostMapping("/createAlgorithm")
    public ResponseEntity<MarketingAlgorithm> createAlgorithm(@RequestBody MarketingAlgorithmRequest request) {
        MarketingAlgorithm algorithm = marketingAlgorithmService.createAlgorithm(request);
        return ResponseEntity.ok(algorithm);
    }

    @DeleteMapping("/deleteAlgorithm/{id}")
    public ResponseEntity<Void> deleteAlgorithm(@PathVariable long id) {
        marketingAlgorithmService.deleteAlgorithm(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/processAlgorithms")
    public ResponseEntity<?> processAlgorithms() {
        marketingAlgorithmService.processAlgorithms();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateAlgorithm")
    public ResponseEntity<MarketingAlgorithm> updateAlgorithm(@RequestBody MarketingAlgorithmRequest request) {
        MarketingAlgorithm algorithm = marketingAlgorithmService.updateAlgorithm(request);
        return ResponseEntity.ok(algorithm);
    }

    @DeleteMapping("/deleteSetsAttributes")
    public ResponseEntity<MarketingAlgorithm> deleteSetsAttributes(@RequestBody MarketingAlgorithmRequest request) {
        MarketingAlgorithm algorithm = marketingAlgorithmService.deleteSetsAttributes(request);
        return ResponseEntity.ok(algorithm);
    }

}


