package org.aicha.citronix.service;

import org.aicha.citronix.domain.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FieldService {
    Field save(Field field);
    Optional<Field> findById(UUID id);
    void delete(UUID id);
    Page<Field> findByFarmId(UUID farmId, Pageable pageable);
    boolean isTreeDensityValid(UUID fieldId, int numberOfTrees);
}
