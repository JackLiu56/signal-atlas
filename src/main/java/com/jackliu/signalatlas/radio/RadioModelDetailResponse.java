package com.jackliu.signalatlas.radio;

import com.jackliu.signalatlas.spec.RadioSpecResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RadioModelDetailResponse {

    private Long id;

    private Long brandId;
    private String brandName;
    private String brandCountry;

    private String modelName;
    private String modelNumber;

    private RadioCategory category;
    private FrequencyBand frequencyBand;

    private String frequencyRange;
    private String modulation;
    private String digitalProtocol;

    private Integer releaseYear;
    private Integer discontinuedYear;

    private BigDecimal estimatedMarketLow;
    private BigDecimal estimatedMarketHigh;

    private String description;
    private String imageUrl;

    private LocalDateTime createdAt;

    private List<RadioSpecResponse> specs;

    public RadioModelDetailResponse(
            RadioModel radioModel,
            List<RadioSpecResponse> specs
    ) {
        this.id = radioModel.getId();

        this.brandId = radioModel.getBrand().getId();
        this.brandName = radioModel.getBrand().getName();
        this.brandCountry = radioModel.getBrand().getCountry();

        this.modelName = radioModel.getModelName();
        this.modelNumber = radioModel.getModelNumber();

        this.category = radioModel.getCategory();
        this.frequencyBand = radioModel.getFrequencyBand();

        this.frequencyRange = radioModel.getFrequencyRange();
        this.modulation = radioModel.getModulation();
        this.digitalProtocol = radioModel.getDigitalProtocol();

        this.releaseYear = radioModel.getReleaseYear();
        this.discontinuedYear = radioModel.getDiscontinuedYear();

        this.estimatedMarketLow = radioModel.getEstimatedMarketLow();
        this.estimatedMarketHigh = radioModel.getEstimatedMarketHigh();

        this.description = radioModel.getDescription();
        this.imageUrl = radioModel.getImageUrl();

        this.createdAt = radioModel.getCreatedAt();

        this.specs = specs;
    }

    public Long getId() {
        return id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getBrandCountry() {
        return brandCountry;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public RadioCategory getCategory() {
        return category;
    }

    public FrequencyBand getFrequencyBand() {
        return frequencyBand;
    }

    public String getFrequencyRange() {
        return frequencyRange;
    }

    public String getModulation() {
        return modulation;
    }

    public String getDigitalProtocol() {
        return digitalProtocol;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Integer getDiscontinuedYear() {
        return discontinuedYear;
    }

    public BigDecimal getEstimatedMarketLow() {
        return estimatedMarketLow;
    }

    public BigDecimal getEstimatedMarketHigh() {
        return estimatedMarketHigh;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<RadioSpecResponse> getSpecs() {
        return specs;
    }
}