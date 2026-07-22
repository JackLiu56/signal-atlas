package com.jackliu.signalatlas.radio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RadioModelService {

    private final RadioModelRepository radioModelRepository;

    public RadioModelService(RadioModelRepository radioModelRepository) {
        this.radioModelRepository = radioModelRepository;
    }

    public List<RadioModel> getAllRadioModels() {
        return radioModelRepository.findAll();
    }

    public RadioModel getRadioModelById(Long id) {
        return radioModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Radio model not found: " + id));
    }

    public RadioModel createRadioModel(RadioModel radioModel) {
        boolean duplicateExists =
                radioModelRepository
                        .findByBrandIgnoreCaseAndModelNameIgnoreCase(
                                radioModel.getBrand(),
                                radioModel.getModelName()
                        )
                        .isPresent();

        if (duplicateExists) {
            throw new RuntimeException(
                    "Duplicate radio model: " +
                            radioModel.getBrand() +
                            " " +
                            radioModel.getModelName()
            );
        }

        return radioModelRepository.save(radioModel);
    }

    public RadioModel updateRadioModel(Long id, RadioModel updatedRadioModel) {
        RadioModel existingRadioModel = getRadioModelById(id);

        existingRadioModel.setBrand(updatedRadioModel.getBrand());
        existingRadioModel.setModelName(updatedRadioModel.getModelName());
        existingRadioModel.setModelNumber(updatedRadioModel.getModelNumber());
        existingRadioModel.setCategory(updatedRadioModel.getCategory());
        existingRadioModel.setFrequencyBand(updatedRadioModel.getFrequencyBand());
        existingRadioModel.setFrequencyRange(updatedRadioModel.getFrequencyRange());
        existingRadioModel.setModulation(updatedRadioModel.getModulation());
        existingRadioModel.setDigitalProtocol(updatedRadioModel.getDigitalProtocol());
        existingRadioModel.setReleaseYear(updatedRadioModel.getReleaseYear());
        existingRadioModel.setDiscontinuedYear(updatedRadioModel.getDiscontinuedYear());
        existingRadioModel.setEstimatedMarketLow(updatedRadioModel.getEstimatedMarketLow());
        existingRadioModel.setEstimatedMarketHigh(updatedRadioModel.getEstimatedMarketHigh());
        existingRadioModel.setDescription(updatedRadioModel.getDescription());
        existingRadioModel.setImageUrl(updatedRadioModel.getImageUrl());

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
                        containsIgnoreCase(radio.getBrand(), searchText)
                                || containsIgnoreCase(radio.getModelName(), searchText)
                                || containsIgnoreCase(radio.getModelNumber(), searchText)
                                || containsIgnoreCase(radio.getFrequencyRange(), searchText)
                                || containsIgnoreCase(radio.getDigitalProtocol(), searchText)
                )
                .toList();
    }

    private boolean containsIgnoreCase(String value, String searchText) {
        return value != null &&
                value.toLowerCase().contains(searchText);
    }
}
