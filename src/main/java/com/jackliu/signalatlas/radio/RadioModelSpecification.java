package com.jackliu.signalatlas.radio;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RadioModelSpecification {

    public static Specification<RadioModel> filter(
            Long brandId,
            RadioCategory category,
            FrequencyBand frequencyBand,
            String digitalProtocol,
            Integer releaseYearFrom,
            Integer releaseYearTo,
            String keyword
    ){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (brandId != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("brand").get("id"),
                                brandId
                        )
                );
            }

            if (category != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("category"),
                                category
                        )
                );
            }

            if (frequencyBand != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("frequencyBand"),
                                frequencyBand
                        )
                );
            }

            if (digitalProtocol != null &&
                    !digitalProtocol.trim().isEmpty()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("digitalProtocol")
                                ),
                                "%" + digitalProtocol.trim().toLowerCase() + "%"
                        )
                );
            }

            if (releaseYearFrom != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("releaseYear"),
                                releaseYearFrom
                        )
                );
            }

            if (releaseYearTo != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("releaseYear"),
                                releaseYearTo
                        )
                );
            }

            if (keyword != null &&
                    !keyword.trim().isEmpty()) {

                String searchText =
                        "%" + keyword.trim().toLowerCase() + "%";

                Predicate brandNameMatch =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("brand").get("name")
                                ),
                                searchText
                        );

                Predicate modelNameMatch =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("modelName")
                                ),
                                searchText
                        );

                Predicate modelNumberMatch =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("modelNumber")
                                ),
                                searchText
                        );

                Predicate frequencyRangeMatch =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("frequencyRange")
                                ),
                                searchText
                        );

                Predicate protocolMatch =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("digitalProtocol")
                                ),
                                searchText
                        );

                predicates.add(
                        criteriaBuilder.or(
                                brandNameMatch,
                                modelNameMatch,
                                modelNumberMatch,
                                frequencyRangeMatch,
                                protocolMatch
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}
