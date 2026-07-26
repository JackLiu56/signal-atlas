package com.jackliu.signalatlas.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Radio Specifications", description = "Technical specification APIs for radio models")
public class RadioSpecController {

    private final RadioSpecService radioSpecService;

    public RadioSpecController(RadioSpecService radioSpecService) {
        this.radioSpecService = radioSpecService;
    }

    @GetMapping("/api/radios/{radioId}/specs")
    public List<RadioSpec> getSpecsByRadioModelId(
            @PathVariable Long radioId
    ) {
        return radioSpecService.getSpecsByRadioModelId(radioId);
    }

    @Operation(
            summary = "Add technical specification",
            description = "Adds one technical specification record to a radio model."
    )
    @PostMapping("/api/radios/{radioId}/specs")
    public RadioSpec addSpecToRadioModel(
            @PathVariable Long radioId,
            @Valid @RequestBody RadioSpecRequest request
    ){
        return  radioSpecService.addSpecToRadioModel(radioId, request);
    }

    @PutMapping("/api/specs/{specId}")
    public RadioSpec updateSpec(
            @PathVariable Long specId,
            @Valid @RequestBody RadioSpecRequest request
    ){
        return radioSpecService.updateSpec(specId, request);
    }

    @DeleteMapping("/api/specs/{specId}")
    public void deleteSpec(
            @PathVariable Long specId
    ){
        radioSpecService.deleteSpec(specId);
    }
}
