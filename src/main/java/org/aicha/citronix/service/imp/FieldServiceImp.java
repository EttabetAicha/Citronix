package org.aicha.citronix.service.imp;


import org.aicha.citronix.domain.Field;
import org.aicha.citronix.repository.FieldRepository;
import org.aicha.citronix.service.FieldService;
import org.aicha.citronix.web.errors.field.FieldNotFoundException;
import org.aicha.citronix.web.errors.field.InvalidFieldAreaException;
import org.aicha.citronix.web.errors.field.InvalidTreeDensityException;
import org.aicha.citronix.web.errors.field.MaxFieldsInFarmException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FieldServiceImp implements FieldService {
    private final FieldRepository fieldRepository;

    public FieldServiceImp(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Field save(Field field) {

        validateFieldArea(field);
        return fieldRepository.save(field);
    }

    @Override
    public void delete(UUID id) {
        if (!fieldRepository.existsById(id)) {
            throw new FieldNotFoundException("field not found");
        }
        fieldRepository.deleteById(id);
    }

    @Override
    public Page<Field> findByFarmId(UUID farmId, Pageable pageable) {
        return fieldRepository.findByFarmId(farmId, pageable);
    }

    public List<Field> findByFatrId(UUID id){
        return fieldRepository.findFieldByFarmId(id);
    }

    @Override
    public boolean isTreeDensityValid(UUID fieldId, int numberOfTrees) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new FieldNotFoundException("field not found"));

        if (!field.isTreeDensityValid(numberOfTrees)) {
            throw new InvalidTreeDensityException(fieldId, numberOfTrees);
        }

        return true;
    }

    public Optional<Field> findById(UUID fieldId) {
        return Optional.ofNullable(fieldRepository.findById(fieldId)
                .orElseThrow(() -> new FieldNotFoundException("Field not found with id: ")));
    }

    private void validateFieldArea(Field field)  {
        double farmArea = field.getFarm().getArea();

        System.out.println(farmArea);
        double maxAllowedSingleFieldArea = field.getFarm().getArea() / 2;
        System.out.println(maxAllowedSingleFieldArea);

        if (field.getFarm().getFields().size() >= 10) {
            throw new MaxFieldsInFarmException("A farm cannot have more than 10 fields");
        }

        if (field.getArea() > maxAllowedSingleFieldArea) {
            throw new InvalidFieldAreaException(
                    String.format("Field area cannot exceed 50%% of the farm's total area. Max allowed: %.2f", maxAllowedSingleFieldArea)
            );
        }

        double totalExistingFieldsArea = fieldRepository.findFieldByFarmId(field.getFarm().getId())
                .stream()
                .mapToDouble(Field::getArea)
                .sum();
        double totalAfterAddingNewField = totalExistingFieldsArea + field.getArea();

        if (totalAfterAddingNewField > farmArea) {
            throw new InvalidFieldAreaException(
                    String.format("Total fields' area cannot exceed the farm's total area. Current total: %.2f, Farm area: %.2f",
                            totalAfterAddingNewField, farmArea)
            );
        }
    }
}
