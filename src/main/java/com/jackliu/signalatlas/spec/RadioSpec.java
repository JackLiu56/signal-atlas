package com.jackliu.signalatlas.spec;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jackliu.signalatlas.radio.RadioModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "radio_specs")
public class RadioSpec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "radio_model_id", nullable = false)
    private RadioModel radioModel;

    @NotBlank
    @Column(nullable = false)
    private String specName;

    @NotBlank
    @Column(nullable = false)
    private String specValue;

    private String unit;

    private String category;

    private Integer displayOrder = 0;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId(){
        return id;
    }

    public RadioModel getRadioModel(){
        return radioModel;
    }

    public void setRadioModel(RadioModel radioModel){
        this.radioModel = radioModel;
    }

    public String getSpecName(){
        return specName;
    }

    public void setSpecName(String specName){
        this.specName = specName;
    }

    public String getSpecValue(){
        return specValue;
    }

    public void setSpecValue(String specValue){
        this.specValue = specValue;
    }

    public String getUnit(){
        return unit;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
