package com.jackliu.signalatlas.brand;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin(origins = "http://localhost:4200")
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