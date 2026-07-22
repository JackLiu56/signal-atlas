package com.jackliu.signalatlas.radio;

import com.jackliu.signalatlas.brand.Brand;
import com.jackliu.signalatlas.brand.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RadioModelService {

    private final RadioModelRepository radioModelRepository;
    private final BrandRepository brandRepository;

    public RadioModelService(
            RadioModelRepository radioModelRepository,
            BrandRepository brandRepository
    ) {
        this.radioModelRepository = radioModelRepository;
        this.brandRepository = brandRepository;
    }

    public List<RadioModel> getAllRadioModels() {
        return radioModelRepository.findAll();
    }

    public RadioModel getRadioModelById(Long id) {
        return radioModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Radio model not found: " + id));
    }

    public RadioModel createRadioModel(RadioModelRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found: " + request.getBrandId()));

        boolean duplicateExists =
                radioModelRepository
                        .findByBrand_IdAndModelNameIgnoreCase(
                                request.getBrandId(),
                                request.getModelName()
                        )
                        .isPresent();

        if (duplicateExists) {
            throw new RuntimeException(
                    "Duplicate radio model: " +
                            brand.getName() +
                            " " +
                            request.getModelName()
            );
        }

        RadioModel radioModel = new RadioModel();

        applyRequestToRadioModel(
                radioModel,
                request,
                brand
        );

        return radioModelRepository.save(radioModel);
    }

    public RadioModel updateRadioModel(
            Long id,
            RadioModelRequest request
    ) {
        RadioModel existingRadioModel =
                getRadioModelById(id);

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found: " + request.getBrandId()));

        radioModelRepository
                .findByBrand_IdAndModelNameIgnoreCase(
                        request.getBrandId(),
                        request.getModelName()
                )
                .ifPresent(duplicate -> {
                    if (!duplicate.getId().equals(id)) {
                        throw new RuntimeException(
                                "Duplicate radio model: " +
                                        brand.getName() +
                                        " " +
                                        request.getModelName()
                        );
                    }
                });

        applyRequestToRadioModel(
                existingRadioModel,
                request,
                brand
        );

        return radioModelRepository.save(existingRadioModel);
    }

    public void deleteRadioModel(Long id) {
        RadioModel existingRadioModel = getRadioModelById(id);
        radioModelRepository.delete(existingRadioModel);
    }

    public List<RadioModel> searchRadioModels(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return radioModelRepository.findAll();
        }

        String searchText = keyword.trim().toLowerCase();

        return radioModelRepository.findAll()
                .stream()
                .filter(radio ->
                        containsIgnoreCase(radio.getBrand().getName(), searchText)
                                || containsIgnoreCase(radio.getModelName(), searchText)
                                || containsIgnoreCase(radio.getModelNumber(), searchText)
                                || containsIgnoreCase(radio.getFrequencyRange(), searchText)
                                || containsIgnoreCase(radio.getDigitalProtocol(), searchText)
                )
                .toList();
    }

    public List<RadioModel> filterRadioModels(
            Long brandId,
            RadioCategory category,
            FrequencyBand frequencyBand,
            String digitalProtocol,
            Integer releaseYearFrom,
            Integer releaseYearTo,
            String keyword
    ) {
        return radioModelRepository.findAll(
                RadioModelSpecification.filter(
                        brandId,
                        category,
                        frequencyBand,
                        digitalProtocol,
                        releaseYearFrom,
                        releaseYearTo,
                        keyword
                )
        );
    }

    private void applyRequestToRadioModel(
            RadioModel radioModel,
            RadioModelRequest request,
            Brand brand
    ) {
        radioModel.setBrand(brand);
        radioModel.setModelName(request.getModelName());
        radioModel.setModelNumber(request.getModelNumber());
        radioModel.setCategory(request.getCategory());
        radioModel.setFrequencyBand(request.getFrequencyBand());
        radioModel.setFrequencyRange(request.getFrequencyRange());
        radioModel.setModulation(request.getModulation());
        radioModel.setDigitalProtocol(request.getDigitalProtocol());
        radioModel.setReleaseYear(request.getReleaseYear());
        radioModel.setDiscontinuedYear(request.getDiscontinuedYear());
        radioModel.setEstimatedMarketLow(request.getEstimatedMarketLow());
        radioModel.setEstimatedMarketHigh(request.getEstimatedMarketHigh());
        radioModel.setDescription(request.getDescription());
        radioModel.setImageUrl(request.getImageUrl());
    }

    private boolean containsIgnoreCase(
            String value,
            String searchText
    ) {
        return value != null &&
                value.toLowerCase().contains(searchText);
    }
}