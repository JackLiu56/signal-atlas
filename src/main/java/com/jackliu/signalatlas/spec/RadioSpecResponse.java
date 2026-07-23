package com.jackliu.signalatlas.spec;

import java.time.LocalDateTime;

public class RadioSpecResponse {

    private Long id;
    private String specName;
    private String specValue;
    private String unit;
    private String category;
    private Integer displayOrder;
    private LocalDateTime createAt;

    public RadioSpecResponse(RadioSpec radioSpec) {
        this.id = radioSpec.getId();
        this.specName = radioSpec.getSpecName();
        this.specValue = radioSpec.getSpecValue();
        this.unit = radioSpec.getUnit();
        this.category = radioSpec.getCategory();
        this.displayOrder = radioSpec.getDisplayOrder();
        this.createAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getSpecName() {
        return specName;
    }

    public String getSpecValue() {
        return specValue;
    }

    public String getUnit() {
        return unit;
    }

    public String getCategory() {
        return category;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
