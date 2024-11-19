package org.aicha.citronix.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.domain.Field;
import org.aicha.citronix.dto.FarmDto;
import org.aicha.citronix.exception.CustomException;
import org.aicha.citronix.mapper.FarmMapper;
import org.aicha.citronix.mapper.FieldMapper;
import org.aicha.citronix.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FarmService {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FarmMapper farmMapper;
    @Autowired
    private FieldMapper fieldMapper;

    public List<FarmDto> getAllFarms() {
        List<Farm> farms = farmRepository.findAll();
        if (farms.isEmpty()) {
            throw new CustomException("No farms found.");
        }
        return farms.stream().map(farmMapper::toDto).collect(Collectors.toList());
    }

    public FarmDto getFarmById(UUID id) {
        Farm farm = farmRepository.findById(id).orElseThrow(() -> new CustomException("Farm not found with id: " + id));
        return farmMapper.toDto(farm);
    }
    public FarmDto createFarmWithFields(FarmDto farmDto) {
        try {
            Farm farm = farmMapper.toEntity(farmDto);
            List<Field> fields = farmDto.getFields().stream()
                    .map(fieldDto -> {
                        Field field = fieldMapper.toEntity(fieldDto);
                        field.setFarm(farm);
                        return field;
                    })
                    .collect(Collectors.toList());
            farm.setFields(fields);
            return farmMapper.toDto(farmRepository.save(farm));
        } catch (Exception e) {
            throw new CustomException("Failed to create farm with fields: " + e.getMessage());
        }
    }

    public FarmDto createFarm(FarmDto farmDto) {
        try {
            Farm farm = farmMapper.toEntity(farmDto);
            return farmMapper.toDto(farmRepository.save(farm));
        } catch (Exception e) {
            throw new CustomException("Failed to create farm: " + e.getMessage());
        }
    }

    public FarmDto updateFarm(FarmDto farmDto) {
        if (!farmRepository.existsById(farmDto.getId())) {
            throw new CustomException("Farm not found with id: " + farmDto.getId());
        }
        try {
            Farm farm = farmMapper.toEntity(farmDto);
            return farmMapper.toDto(farmRepository.save(farm));
        } catch (Exception e) {
            throw new CustomException("Failed to update farm: " + e.getMessage());
        }
    }

    public void deleteFarm(UUID id) {
        if (!farmRepository.existsById(id)) {
            throw new CustomException("Farm not found with id: " + id);
        }
        try {
            farmRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Failed to delete farm: " + e.getMessage());
        }
    }

    public List<FarmDto> findByNameAndLocation(String name, String location) {
        List<Farm> farms = farmRepository.findByNameAndLocation(name, location);
        if (farms.isEmpty()) {
            throw new CustomException("No farms found with the given name and location.");
        }
        return farms.stream().map(farmMapper::toDto).collect(Collectors.toList());
    }

    public List<FarmDto> searchFarms(String name, String location, Double area) {
        List<Farm> farms = farmRepository.findAll((Root<Farm> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate namePredicate = cb.like(root.get("name"), "%" + name + "%");
            Predicate locationPredicate = cb.like(root.get("location"), "%" + location + "%");
            Predicate areaPredicate = cb.equal(root.get("area"), area);
            return cb.and(namePredicate, locationPredicate, areaPredicate);
        });

        if (farms.isEmpty()) {
            throw new CustomException("No farms found with the given criteria.");
        }

        return farms.stream().map(farmMapper::toDto).collect(Collectors.toList());
    }
}