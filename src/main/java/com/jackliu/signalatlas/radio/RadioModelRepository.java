package com.jackliu.signalatlas.radio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RadioModelRepository extends
        JpaRepository<RadioModel, Long>,
        JpaSpecificationExecutor<RadioModel> {

    Optional<RadioModel> findByBrand_IdAndModelNameIgnoreCase(
            Long brandId,
            String modelName
    );

    List<RadioModel> findByBrand_NameContainingIgnoreCase(String brandName);

    List<RadioModel> findByModelNameContainingIgnoreCase(String modelName);
}