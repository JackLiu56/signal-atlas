package com.jackliu.signalatlas.radio;

import java.math.BigDecimal;

public class RadioModelSummaryResponse {

    private Long id;

    private Long brandId;
    private String brandName;

    private String modelName;
    private String modelNumber;

    private RadioCategory category;
    private FrequencyBand frequencyBand;

    private String frequencyRange;
    private String digitalProtocol;

    private Integer releaseYear;
    private Integer discontinuedYear;

    private BigDecimal estimatedMarketLow;
    private BigDecimal estimatedMarketHigh;

    private String imageUrl;

    public RadioModelSummaryResponse(RadioModel radioModel){
        this.id = radioModel.getId();

        this.brandId = radioModel.getBrand().getId();
        this.brandName = radioModel.getBrand().getName();

        this.modelName = radioModel.getModelName();
        this.modelNumber = radioModel.getModelNumber();

        this.category = radioModel.getCategory();
        this.frequencyBand = radioModel.getFrequencyBand();

        this.frequencyRange = radioModel.getFrequencyRange();
        this.digitalProtocol = radioModel.getDigitalProtocol();

        this.releaseYear = radioModel.getReleaseYear();
        this.discontinuedYear = radioModel.getDiscontinuedYear();

        this.estimatedMarketLow = radioModel.getEstimatedMarketLow();
        this.estimatedMarketHigh = radioModel.getEstimatedMarketHigh();

        this.imageUrl = radioModel.getImageUrl();
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

    public String getImageUrl() {
        return imageUrl;
    }
}
