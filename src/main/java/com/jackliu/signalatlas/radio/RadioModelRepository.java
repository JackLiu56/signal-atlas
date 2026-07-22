package com.jackliu.signalatlas.radio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RadioModelRepository extends JpaRepository<RadioModel, Long> {

    Optional<RadioModel> findByBrandIgnoreCaseAndModelNameIgnoreCase(
            String brand,
            String modelName
    );

    List<RadioModel> findByBrandContainingIgnoreCase(String brand);

    List<RadioModel> findByModelNameContainingIgnoreCase(String modelName);
}
