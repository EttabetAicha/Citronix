package org.aicha.citronix.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.domain.Field;
import org.aicha.citronix.dto.FarmDto;
import org.aicha.citronix.dto.FieldDto;
import org.aicha.citronix.exception.CustomException;
import org.aicha.citronix.mapper.FieldMapper;
import org.aicha.citronix.repository.FarmRepository;
import org.aicha.citronix.repository.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FieldService {

    private FieldRepository fieldRepository;
    private FarmRepository farmRepository;
    private FieldMapper fieldMapper;
    private Validator validator;

    public FieldService(FieldRepository fieldRepository, FarmRepository farmRepository, FieldMapper fieldMapper, Validator validator) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
        this.fieldMapper = fieldMapper;
        this.validator = validator;
    }

    public List<FieldDto> getAllFields() {
        List<Field> fields = fieldRepository.findAll();
        if (fields.isEmpty()) {
            throw new CustomException("No fields found.");
        }
        return fields.stream().map(fieldMapper::toDto).collect(Collectors.toList());
    }

    public FieldDto getFieldById(UUID id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new CustomException("Field not found with id: " + id));
        return fieldMapper.toDto(field);
    }

    public FieldDto createField(FieldDto fieldDto, UUID farmId) {
        validateFieldDto(fieldDto);
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new CustomException("Farm not found with id: " + farmId));
        try {
            Field field = fieldMapper.toEntity(fieldDto);
            field.setFarm(farm);
            return fieldMapper.toDto(fieldRepository.save(field));
        } catch (Exception e) {
            throw new CustomException("Failed to create field: " + e.getMessage());
        }
    }

    public FieldDto updateField(FieldDto fieldDto, UUID farmId) {
        if (!fieldRepository.existsById(fieldDto.getId())) {
            throw new CustomException("Field not found with id: " + fieldDto.getId());
        }
        validateFieldDto(fieldDto);
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new CustomException("Farm not found with id: " + farmId));
        try {
            Field field = fieldMapper.toEntity(fieldDto);
            field.setFarm(farm);
            return fieldMapper.toDto(fieldRepository.save(field));
        } catch (Exception e) {
            throw new CustomException("Failed to update field: " + e.getMessage());
        }
    }

    public void deleteField(UUID id) {
        if (!fieldRepository.existsById(id)) {
            throw new CustomException("Field not found with id: " + id);
        }
        try {
            fieldRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Failed to delete field: " + e.getMessage());
        }
    }
    public void validateTotalFieldArea(Farm farm, List<Field> fields) {
        double totalFieldArea = fields.stream().mapToDouble(Field::getArea).sum();
        if (totalFieldArea >= farm.getArea()) {
            throw new CustomException("The total area of fields must be strictly less than the area of the farm.");
        }
    }

    public List<FieldDto> findByFarmId(UUID farmId) {
        List<Field> fields = fieldRepository.findByFarmId(farmId);
        if (fields.isEmpty()) {
            throw new CustomException("No fields found for the given farm id.");
        }
        return fields.stream().map(fieldMapper::toDto).collect(Collectors.toList());
    }

    public FarmDto associateFieldsToFarm(FarmDto farmDto) {
        Farm farm = farmRepository.findById(farmDto.getId())
                .orElseThrow(() -> new CustomException("Farm not found with id: " + farmDto.getId()));
        List<Field> fields = farmDto.getFields().stream().map(fieldMapper::toEntity).collect(Collectors.toList());
        for (Field field : fields) {
            if (field.getFarm() != null && field.getFarm().getId() != null && !field.getFarm().getId().equals(farm.getId())) {
                throw new CustomException("Field with id: " + field.getId() + " is already associated with another farm.");
            }
        }
        validateTotalFieldArea(farm, fields);
        fields.forEach(field -> field.setFarm(farm));
        fieldRepository.saveAll(fields);
        return farmDto;
    }
    private void validateFieldDto(FieldDto fieldDto) {
        Set<ConstraintViolation<FieldDto>> violations = validator.validate(fieldDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}