package com.jackliu.signalatlas.spec;

import com.jackliu.signalatlas.exception.ResourceNotFoundException;
import com.jackliu.signalatlas.radio.RadioModel;
import com.jackliu.signalatlas.radio.RadioModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RadioSpecService {

    private RadioSpecRepository radioSpecRepository;
    private RadioModelRepository radioModelRepository;

    public RadioSpecService(
            RadioSpecRepository radioSpecRepository,
            RadioModelRepository radioModelRepository){
        this.radioSpecRepository = radioSpecRepository;
        this.radioModelRepository = radioModelRepository;
    }

    public List<RadioSpec> getSpecsByRadioModelId(Long radioModelId) {
        return radioSpecRepository.findByRadioModel_IdOrderByDisplayOrderAsc(radioModelId);
    }

    public RadioSpec addSpecToRadioModel(Long radioModelId, RadioSpecRequest request) {
        RadioModel radioModel = radioModelRepository.findById(radioModelId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Radio model not found: " + radioModelId
                ));

        RadioSpec spec = new RadioSpec();

        spec.setRadioModel(radioModel);
        applyRequestToSpec(spec, request);

        return radioSpecRepository.save(spec);
    }

    public RadioSpec updateSpec(Long specId, RadioSpecRequest request) {
        RadioSpec existingSpec = radioSpecRepository.findById(specId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Radio spec not found: " + specId
                ));

        applyRequestToSpec(existingSpec, request);

        return radioSpecRepository.save(existingSpec);
    }

    public void deleteSpec(Long specId) {
        RadioSpec existingSpec = radioSpecRepository.findById(specId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Radio spec not found: " + specId
                ));

        radioSpecRepository.delete(existingSpec);
    }

    private void applyRequestToSpec(RadioSpec spec, RadioSpecRequest request) {
        spec.setSpecName(request.getSpecName());
        spec.setSpecValue(request.getSpecValue());
        spec.setUnit(request.getUnit());
        spec.setCategory(request.getCategory());

        if (request.getDisplayOrder() == null){
            spec.setDisplayOrder(0);
        } else {
            spec.setDisplayOrder(request.getDisplayOrder());
        }
    }
}
