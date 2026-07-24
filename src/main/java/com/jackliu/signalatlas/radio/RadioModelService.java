package com.jackliu.signalatlas.radio;

import com.jackliu.signalatlas.brand.Brand;
import com.jackliu.signalatlas.brand.BrandRepository;
import com.jackliu.signalatlas.common.PagedResponse;
import com.jackliu.signalatlas.exception.DuplicateResourceException;
import com.jackliu.signalatlas.exception.ResourceNotFoundException;
import com.jackliu.signalatlas.spec.RadioSpecRepository;
import com.jackliu.signalatlas.spec.RadioSpecResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RadioModelService {

    private final RadioModelRepository radioModelRepository;
    private final BrandRepository brandRepository;
    private final RadioSpecRepository radioSpecRepository;

    public RadioModelService(
            RadioModelRepository radioModelRepository,
            BrandRepository brandRepository,
            RadioSpecRepository radioSpecRepository
    ) {
        this.radioModelRepository = radioModelRepository;
        this.brandRepository = brandRepository;
        this.radioSpecRepository = radioSpecRepository;
    }

    public List<RadioModel> getAllRadioModels() {
        return radioModelRepository.findAll();
    }

    public RadioModel getRadioModelById(Long id) {
        return radioModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Radio model not found: " + id));
    }

    public RadioModelDetailResponse getRadioModelDetailById(Long id) {
        RadioModel radioModel = getRadioModelById(id);

        List<RadioSpecResponse> specs =
                radioSpecRepository
                        .findByRadioModel_IdOrderByDisplayOrderAsc(id)
                        .stream()
                        .map(RadioSpecResponse::new)
                        .toList();

        return new RadioModelDetailResponse(
                radioModel,
                specs
        );
    }

    public RadioModel createRadioModel(RadioModelRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found: " + request.getBrandId()));

        boolean duplicateExists =
                radioModelRepository
                        .findByBrand_IdAndModelNameIgnoreCase(
                                request.getBrandId(),
                                request.getModelName()
                        )
                        .isPresent();

        if (duplicateExists) {
            throw new DuplicateResourceException(
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
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found: " + request.getBrandId()));

        radioModelRepository
                .findByBrand_IdAndModelNameIgnoreCase(
                        request.getBrandId(),
                        request.getModelName()
                )
                .ifPresent(duplicate -> {
                    if (!duplicate.getId().equals(id)) {
                        throw new DuplicateResourceException(
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

    public PagedResponse<RadioModelSummaryResponse> getRadioCatalog(
            Long brandId,
            RadioCategory category,
            FrequencyBand frequencyBand,
            String digitalProtocol,
            Integer releaseYearFrom,
            Integer releaseYearTo,
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    ){
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Sort.Direction sortDirection =
                "desc".equalsIgnoreCase(direction)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        String safeSortBy = normalizeSortBy(sortBy);

        Pageable pageable = PageRequest.of(
                safePage,
                safeSize,
                Sort.by(sortDirection, safeSortBy)
        );

        Page<RadioModel> radioModelPage =
                radioModelRepository.findAll(
                        RadioModelSpecification.filter(
                                brandId,
                                category,
                                frequencyBand,
                                digitalProtocol,
                                releaseYearFrom,
                                releaseYearTo,
                                keyword
                                ),
                        pageable
                );

        List<RadioModelSummaryResponse> content =
                radioModelPage
                        .getContent()
                        .stream()
                        .map(RadioModelSummaryResponse::new)
                        .toList();

        return new PagedResponse<>(
                content,
                radioModelPage.getNumber(),
                radioModelPage.getSize(),
                radioModelPage.getTotalElements(),
                radioModelPage.getTotalPages(),
                radioModelPage.isFirst(),
                radioModelPage.isLast()
        );
    };

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

    private String normalizeSortBy(String sortBy) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return "modelName";
        }

        return switch (sortBy){
            case "brandName" -> "brandName";
            case "modelName" -> "modelName";
            case "releaseYear" -> "releaseYear";
            case "estimatedMarketLow" -> "estimatedMarketLow";
            case "estimatedMarketHigh" -> "estimatedMarketHigh";
            case "createdAt" -> "createdAt";
            default -> "modelName";
        };
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