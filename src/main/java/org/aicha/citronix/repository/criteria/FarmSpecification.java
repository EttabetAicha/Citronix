package org.aicha.citronix.repository.criteria;


import org.aicha.citronix.domain.Farm;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FarmSpecification {
    public static Specification<Farm> nameContains(String name) {
        return(root,query,cd)->
                name != null && !name.isEmpty()
                ? cd.like(cd.lower(root.get("name")), "%"+name.toLowerCase()+"%")
                : null;
    }
    public static Specification<Farm> locationContains( String location) {
        return (root,query,cb)->
                location != null && !location.isEmpty()
                ? cb.like(cb.lower(root.get("location")), "%"+location.toLowerCase()+"%")
                : null;
    }
    public static Specification<Farm> creationDateAfter(LocalDate creationDate) {
        return (root, query, cb) ->
                creationDate != null
                        ? cb.greaterThanOrEqualTo(root.get("creationDate"), creationDate)
                        : null;
    }
}
