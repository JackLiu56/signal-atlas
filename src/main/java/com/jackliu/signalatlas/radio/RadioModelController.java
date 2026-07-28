package com.jackliu.signalatlas.radio;

import com.jackliu.signalatlas.common.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/radios")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Radio Models", description = "Radio model catalog, search, filter, and detail APIs")
public class RadioModelController {

    private final RadioModelService radioModelService;

    public RadioModelController(RadioModelService radioModelService) {
        this.radioModelService = radioModelService;
    }

    @GetMapping
    public List<RadioModel> getAllRadioModels() {
        return radioModelService.getAllRadioModels();
    }

    @Operation(
            summary = "Get radio model details",
            description = "Returns radio model information, brand information, and technical specifications in one response."
    )
    @GetMapping("/{id}/details")
    public RadioModelDetailResponse getRadioModelDetailById(
            @PathVariable long id
    ){
        return radioModelService.getRadioModelDetailById(id);
    }

    @Operation(
            summary = "Get paginated radio catalog",
            description = "Returns a paginated and sortable radio catalog with optional filters for brand, category, frequency band, protocol, release year, and keyword."
    )
    @GetMapping("/catalog")
    public PagedResponse<RadioModelSummaryResponse> getRadioCatalog(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) RadioCategory category,
            @RequestParam(required = false) FrequencyBand frequencyBand,
            @RequestParam(required = false) String digitalProtocol,
            @RequestParam(required = false) Integer releaseYearFrom,
            @RequestParam(required = false) Integer releaseYearTo,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "modelName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        return radioModelService.getRadioCatalog(
                brandId,
                category,
                frequencyBand,
                digitalProtocol,
                releaseYearFrom,
                releaseYearTo,
                q,
                page,
                size,
                sortBy,
                direction
        );
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

    @GetMapping("/filter")
    public List<RadioModel> filterRadioModels(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) RadioCategory category,
            @RequestParam(required = false) FrequencyBand frequencyBand,
            @RequestParam(required = false) String digitalProtocol,
            @RequestParam(required = false) Integer releaseYearFrom,
            @RequestParam(required = false) Integer releaseYearTo,
            @RequestParam(required = false) String q
    ) {
        return radioModelService.filterRadioModels(
                brandId,
                category,
                frequencyBand,
                digitalProtocol,
                releaseYearFrom,
                releaseYearTo,
                q
        );
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
    public ResponseEntity<Map<String, String>> deleteRadioModel(@PathVariable Long id) {
        radioModelService.deleteRadioModel(id);

        return ResponseEntity.ok(Map.of(
                "message", "Radio model deleted successfully",
                "deletedRadioModelId", id.toString()
        ));
    }
}