package org.aicha.citronix.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.domain.Field;
import org.aicha.citronix.dto.FarmDto;
import org.aicha.citronix.dto.FieldDto;
import org.aicha.citronix.exception.CustomException;
import org.aicha.citronix.mapper.FarmMapper;
import org.aicha.citronix.mapper.FieldMapper;
import org.aicha.citronix.repository.FarmRepository;
import org.aicha.citronix.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FieldMapper fieldMapper;
    @Autowired
    private FarmMapper farmMapper;

    @Autowired
    private Validator validator;

    public List<FieldDto> getAllFields() {
        List<Field> fields = fieldRepository.findAll();
        if (fields.isEmpty()) {
            throw new CustomException("No fields found.");
        }
        return fields.stream().map(fieldMapper::toDto).collect(Collectors.toList());
    }

    public FieldDto getFieldById(Integer id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new CustomException("Field not found with id: " + id));
        return fieldMapper.toDto(field);
    }

    public FieldDto createField(FieldDto fieldDto) {
        validateFieldDto(fieldDto);
        Farm farm = farmRepository.findById(fieldDto.getFarmId()).orElseThrow(() -> new CustomException("Farm not found with id: " + fieldDto.getFarmId()));
        try {
            Field field = fieldMapper.toEntity(fieldDto);
            field.setFarm(farm);
            return fieldMapper.toDto(fieldRepository.save(field));
        } catch (Exception e) {
            throw new CustomException("Failed to create field: " + e.getMessage());
        }
    }

    public FieldDto updateField(FieldDto fieldDto) {
        if (!fieldRepository.existsById(fieldDto.getId())) {
            throw new CustomException("Field not found with id: " + fieldDto.getId());
        }
        validateFieldDto(fieldDto);
        Farm farm = farmRepository.findById(fieldDto.getFarmId()).orElseThrow(() -> new CustomException("Farm not found with id: " + fieldDto.getFarmId()));
        try {
            Field field = fieldMapper.toEntity(fieldDto);
            field.setFarm(farm);
            return fieldMapper.toDto(fieldRepository.save(field));
        } catch (Exception e) {
            throw new CustomException("Failed to update field: " + e.getMessage());
        }
    }

    public void deleteField(Integer id) {
        if (!fieldRepository.existsById(id)) {
            throw new CustomException("Field not found with id: " + id);
        }
        try {
            fieldRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Failed to delete field: " + e.getMessage());
        }
    }

    public List<FieldDto> findByFarmId(Integer farmId) {
        List<Field> fields = fieldRepository.findByFarmId(farmId);
        if (fields.isEmpty()) {
            throw new CustomException("No fields found for the given farm id.");
        }
        return fields.stream().map(fieldMapper::toDto).collect(Collectors.toList());
    }

    private void validateFieldDto(FieldDto fieldDto) {
        Set<ConstraintViolation<FieldDto>> violations = validator.validate(fieldDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public List<FarmDto> getFarmsWithFieldAreaGreaterThan4000() {
        List<Farm> farms = farmRepository.findAll();
        List<Farm> filteredFarms = farms.stream()
                .filter(farm -> farm.getFields().stream().mapToDouble(Field::getArea).sum() < 4000)
                .toList();

        if (filteredFarms.isEmpty()) {
            throw new CustomException("No farms found with field area greater than 4000.");
        }
        return filteredFarms.stream().map(farmMapper::toDto).collect(Collectors.toList());
    }
}