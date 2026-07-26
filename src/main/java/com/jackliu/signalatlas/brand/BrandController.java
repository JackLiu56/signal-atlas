package com.jackliu.signalatlas.brand;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Brands", description = "Brand management APIs")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    @Operation(
            summary = "Create a brand",
            description = "Creates a new radio manufacturer brand. Brand names must be unique."
    )
    @PostMapping
    public Brand createBrand(@Valid @RequestBody Brand brand) {
        return brandService.createBrand(brand);
    }

    @PutMapping("/{id}")
    public Brand updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody Brand brand
    ) {
        return brandService.updateBrand(id, brand);
    }

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }
}