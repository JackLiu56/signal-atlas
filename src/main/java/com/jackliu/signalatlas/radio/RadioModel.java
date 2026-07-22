package com.jackliu.signalatlas.radio;

import com.jackliu.signalatlas.brand.Brand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "radio_models")
public class RadioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @NotBlank
    @Column(nullable = false)
    private String modelName;

    private String modelNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RadioCategory category = RadioCategory.HANDHELD;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FrequencyBand frequencyBand = FrequencyBand.UNKNOWN;

    private String frequencyRange;

    private String modulation;

    private String digitalProtocol;

    private Integer releaseYear;

    private Integer discontinuedYear;

    private BigDecimal estimatedMarketLow;

    private BigDecimal estimatedMarketHigh;

    @Column(length = 2000)
    private String description;

    private String imageUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
