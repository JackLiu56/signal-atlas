package com.jackliu.signalatlas.radio;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radios")
@CrossOrigin(origins = "http://localhost:4200")
public class RadioModelController {

    private final RadioModelService radioModelService;

    public RadioModelController(RadioModelService radioModelService) {
        this.radioModelService = radioModelService;
    }

    @GetMapping
    public List<RadioModel> getAllRadioModels() {
        return radioModelService.getAllRadioModels();
    }

    @GetMapping("/{id}")
    public RadioModel getRadioModelById(@PathVariable Long id) {
        return radioModelService.getRadioModelById(id);
    }

    @GetMapping("/search")
    public List<RadioModel> searchRadioModels(
            @RequestParam(required = false) String q
    ) {
        return radioModelService.searchRadioModels(q);
    }

    @PostMapping
    public RadioModel createRadioModel(
            @Valid @RequestBody RadioModelRequest request
    ) {
        return radioModelService.createRadioModel(request);
    }

    @PutMapping("/{id}")
    public RadioModel updateRadioModel(
            @PathVariable Long id,
            @Valid @RequestBody RadioModelRequest request
    ) {
        return radioModelService.updateRadioModel(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRadioModel(@PathVariable Long id) {
        radioModelService.deleteRadioModel(id);
    }
}