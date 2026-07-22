package com.jackliu.signalatlas.radio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RadioModelRepository extends JpaRepository<RadioModel, Long> {

    Optional<RadioModel> findByBrand_IdAndModelNameIgnoreCase(
            Long brandId,
            String modelName
    );

    List<RadioModel> findByBrand_NameContainingIgnoreCase(String brandName);

    List<RadioModel> findByModelNameContainingIgnoreCase(String modelName);
}