package com.jackliu.signalatlas.radio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class RadioModelRequest {

    @NotNull
    private Long brandId;

    @NotBlank
    private String modelName;

    private String modelNumber;

    private RadioCategory category = RadioCategory.HANDHELD;

    private FrequencyBand frequencyBand = FrequencyBand.UNKNOWN;

    private String frequencyRange;

    private String modulation;

    private String digitalProtocol;

    private Integer releaseYear;

    private Integer discontinuedYear;

    private BigDecimal estimatedMarketLow;

    private BigDecimal estimatedMarketHigh;

    private String description;

    private String imageUrl;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public RadioCategory getCategory() {
        return category;
    }

    public void setCategory(RadioCategory category) {
        this.category = category;
    }

    public FrequencyBand getFrequencyBand() {
        return frequencyBand;
    }

    public void setFrequencyBand(FrequencyBand frequencyBand) {
        this.frequencyBand = frequencyBand;
    }

    public String getFrequencyRange() {
        return frequencyRange;
    }

    public void setFrequencyRange(String frequencyRange) {
        this.frequencyRange = frequencyRange;
    }

    public String getModulation() {
        return modulation;
    }

    public void setModulation(String modulation) {
        this.modulation = modulation;
    }

    public String getDigitalProtocol() {
        return digitalProtocol;
    }

    public void setDigitalProtocol(String digitalProtocol) {
        this.digitalProtocol = digitalProtocol;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getDiscontinuedYear() {
        return discontinuedYear;
    }

    public void setDiscontinuedYear(Integer discontinuedYear) {
        this.discontinuedYear = discontinuedYear;
    }

    public BigDecimal getEstimatedMarketLow() {
        return estimatedMarketLow;
    }

    public void setEstimatedMarketLow(BigDecimal estimatedMarketLow) {
        this.estimatedMarketLow = estimatedMarketLow;
    }

    public BigDecimal getEstimatedMarketHigh() {
        return estimatedMarketHigh;
    }

    public void setEstimatedMarketHigh(BigDecimal estimatedMarketHigh) {
        this.estimatedMarketHigh = estimatedMarketHigh;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}