package com.jackliu.signalatlas.seed;

import com.jackliu.signalatlas.brand.Brand;
import com.jackliu.signalatlas.brand.BrandRepository;
import com.jackliu.signalatlas.radio.FrequencyBand;
import com.jackliu.signalatlas.radio.RadioCategory;
import com.jackliu.signalatlas.radio.RadioModel;
import com.jackliu.signalatlas.radio.RadioModelRepository;
import com.jackliu.signalatlas.spec.RadioSpec;
import com.jackliu.signalatlas.spec.RadioSpecRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final RadioModelRepository radioModelRepository;
    private final RadioSpecRepository radioSpecRepository;


    public DataSeeder(BrandRepository brandRepository, RadioModelRepository radioModelRepository, RadioSpecRepository radioSpecRepository) {
        this.brandRepository = brandRepository;
        this.radioModelRepository = radioModelRepository;
        this.radioSpecRepository = radioSpecRepository;
    }

    @Override
    public void run(String... args) {
        Brand motorola = findOrCreateBrand(
                "Motorola",
                "United States",
                1928,
                "https://www.motorolasolutions.com",
                "A major manufacturer of two-way radios, public safety communication systems, and land mobile radio equipment."
        );

        Brand kenwood = findOrCreateBrand(
                "Kenwood",
                "Japan",
                1946,
                "https://www.kenwood.com",
                "A Japanese manufacturer known for communication equipment, amateur radios, two-way radios, audio products, and electronics."
        );

        Brand icom = findOrCreateBrand(
                "Icom",
                "Japan",
                1954,
                "https://www.icomjapan.com",
                "A Japanese manufacturer of radio communication equipment, including amateur, marine, aviation, and land mobile radios."
        );

        RadioModel xts5000 = findOrCreateRadioModel(
                motorola,
                "XTS 5000",
                "H18UCH9PW7AN",
                RadioCategory.HANDHELD,
                FrequencyBand.UHF,
                "380-470 MHz",
                "FM / P25",
                "APCO P25",
                2002,
                2014,
                new BigDecimal("150"),
                new BigDecimal("450"),
                "A professional portable two-way radio widely used in public safety and land mobile radio systems."
        );

        addSpecsIfMissing(
                xts5000,
                List.of(
                        createSpec("Output Power", "1-5", "W", "RF", 1),
                        createSpec("Channel Capacity", "1000", "channels", "General", 2),
                        createSpec("Digital Protocol", "APCO P25", "", "Protocol", 3)
                )
        );

        RadioModel tk8180 = findOrCreateRadioModel(
                kenwood,
                "TK-8180",
                "TK-8180",
                RadioCategory.MOBILE,
                FrequencyBand.UHF,
                "400-470 MHz",
                "FM",
                "Analog",
                2005,
                2015,
                new BigDecimal("80"),
                new BigDecimal("220"),
                "A Kenwood professional mobile two-way radio commonly used in commercial and public service communication systems."
        );

        addSpecsIfMissing(
                tk8180,
                List.of(
                        createSpec("Output Power", "25-45", "W", "RF", 1),
                        createSpec("Channel Capacity", "512", "channels", "General", 2),
                        createSpec("Digital Protocol", "Analog", "", "Protocol", 3)
                )
        );

        RadioModel icF5061 = findOrCreateRadioModel(
                icom,
                "IC-F5061",
                "IC-F5061",
                RadioCategory.MOBILE,
                FrequencyBand.VHF,
                "136-174 MHz",
                "FM / NXDN",
                "NXDN",
                2008,
                2018,
                new BigDecimal("120"),
                new BigDecimal("300"),
                "An Icom professional mobile radio used in land mobile radio and commercial communication systems."
        );

        addSpecsIfMissing(
                icF5061,
                List.of(
                        createSpec("Output Power", "50", "W", "RF", 1),
                        createSpec("Channel Capacity", "512", "channels", "General", 2),
                        createSpec("Digital Protocol", "NXDN", "", "Protocol", 3)
                )
        );
    }

    private Brand findOrCreateBrand(
            String name,
            String country,
            Integer foundedYear,
            String websiteUrl,
            String description
    ){
        return brandRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Brand brand = new Brand();
                    brand.setName(name);
                    brand.setCountry(country);
                    brand.setFoundedYear(foundedYear);
                    brand.setWebsiteUrl(websiteUrl);
                    brand.setDescription(description);
                    return brandRepository.save(brand);
                });
    }

    private RadioModel findOrCreateRadioModel(
            Brand brand,
            String modelName,
            String modelNumber,
            RadioCategory category,
            FrequencyBand frequencyBand,
            String frequencyRange,
            String modulation,
            String digitalProtocol,
            Integer releaseYear,
            Integer discontinuedYear,
            BigDecimal estimatedMarketLow,
            BigDecimal estimatedMarketHigh,
            String description
    ){
        return radioModelRepository
                .findByBrand_IdAndModelNameIgnoreCase(
                        brand.getId(),
                        modelName
                )
                .orElseGet(() -> {
                    RadioModel radioModel = new RadioModel();
                    radioModel.setBrand(brand);
                    radioModel.setModelName(modelName);
                    radioModel.setModelNumber(modelNumber);
                    radioModel.setCategory(category);
                    radioModel.setFrequencyBand(frequencyBand);
                    radioModel.setFrequencyRange(frequencyRange);
                    radioModel.setModulation(modulation);
                    radioModel.setDigitalProtocol(digitalProtocol);
                    radioModel.setReleaseYear(releaseYear);
                    radioModel.setDiscontinuedYear(discontinuedYear);
                    radioModel.setEstimatedMarketLow(estimatedMarketLow);
                    radioModel.setEstimatedMarketHigh(estimatedMarketHigh);
                    radioModel.setDescription(description);
                    radioModel.setImageUrl("");
                    return radioModelRepository.save(radioModel);
                });
    }

    private RadioSpec createSpec(
            String specName,
            String specValue,
            String unit,
            String category,
            Integer displayOrder
    ){
        RadioSpec spec = new RadioSpec();
        spec.setSpecName(specName);
        spec.setSpecValue(specValue);
        spec.setUnit(unit);
        spec.setCategory(category);
        spec.setDisplayOrder(displayOrder);
        return spec;
    }

    private void addSpecsIfMissing(
            RadioModel radioModel,
            List<RadioSpec> specs
    ){
        for (RadioSpec spec : specs) {
            boolean exists = radioSpecRepository.existsByRadioModel_IdAndSpecNameIgnoreCase(
                    radioModel.getId(),
                    spec.getSpecName()
            );

            if (!exists) {
                spec.setRadioModel(radioModel);
                radioSpecRepository.save(spec);
            }
        }
    }
}
