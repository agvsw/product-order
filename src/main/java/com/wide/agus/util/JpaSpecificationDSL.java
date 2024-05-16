package com.wide.agus.util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class JpaSpecificationDSL {
    public static <T> Specification<T> or(List<Specification<T>> specifications) {
        return (root, query, builder) -> {
            Predicate[] predicates = specifications.stream()
                    .map(specification -> specification.toPredicate(root, query, builder))
                    .toArray(Predicate[]::new);

            return builder.or(predicates);
        };
    }
}
