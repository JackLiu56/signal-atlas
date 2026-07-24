package com.jackliu.signalatlas.brand;

import com.jackliu.signalatlas.exception.DuplicateResourceException;
import com.jackliu.signalatlas.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found: " + id));
    }

    public Brand createBrand(Brand brand) {
        if (brandRepository.existsByNameIgnoreCase(brand.getName())) {
            throw new DuplicateResourceException("Duplicate brand name: " + brand.getName());
        }

        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, Brand updatedBrand) {
        Brand existingBrand = getBrandById(id);

        existingBrand.setName(updatedBrand.getName());
        existingBrand.setCountry(updatedBrand.getCountry());
        existingBrand.setFoundedYear(updatedBrand.getFoundedYear());
        existingBrand.setWebsiteUrl(updatedBrand.getWebsiteUrl());
        existingBrand.setLogoUrl(updatedBrand.getLogoUrl());
        existingBrand.setDescription(updatedBrand.getDescription());

        return brandRepository.save(existingBrand);
    }

    public void deleteBrand(Long id) {
        Brand existingBrand = getBrandById(id);
        brandRepository.delete(existingBrand);
    }
}