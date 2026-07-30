package com.jackliu.signalatlas.spec;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RadioSpecRepository extends JpaRepository<RadioSpec, Long> {
    List<RadioSpec> findByRadioModel_IdOrderByDisplayOrderAsc(Long radioModelId);
    void deleteByRadioModel_Id(Long radioModelId);
    boolean existsByRadioModel_IdAndSpecNameIgnoreCase(
            Long radioModelId,
            String specName
    );
}
