package it.unicam.cs.ids.lp.marketing.personalizedmodels;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messageModel")
public class ModelController {

    private final ModelService modelService;

    @PostMapping("/createModel")
    public ResponseEntity<MessageModel> createModel(@RequestBody ModelRequest request) {
        MessageModel messageModel = modelService.createModel(request);
        return new ResponseEntity<>(messageModel, HttpStatus.CREATED);
    }

    @DeleteMapping("/{modelId}")
    public ResponseEntity<?> deleteModel(@PathVariable long modelId) {
        modelService.deleteModel(modelId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{modelId}")
    public ResponseEntity<MessageModel> findModelById(@PathVariable long modelId) {
        MessageModel messageModel = modelService.findModelById(modelId);
        return new ResponseEntity<>(messageModel, HttpStatus.OK);
    }

    @GetMapping("/findByName/{modelName}")
    public ResponseEntity<MessageModel> findModelByName(@PathVariable String modelName) {
        MessageModel messageModel = modelService.findModelByName(modelName);
        return new ResponseEntity<>(messageModel, HttpStatus.OK);
    }
}
