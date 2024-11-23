package org.aicha.citronix.service.imp;


import jakarta.validation.Valid;
import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.domain.Field;
import org.aicha.citronix.repository.FarmRepository;
import org.aicha.citronix.repository.criteria.FarmSpecification;
import org.aicha.citronix.service.FarmService;
import org.aicha.citronix.service.FieldService;
import org.aicha.citronix.web.errors.farm.FarmNotFoundException;
import org.aicha.citronix.web.errors.farm.InvalidFarmException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FarmServiceImp implements FarmService {

    private final FarmRepository farmRepository;
    private final FieldService fieldService;

    public FarmServiceImp(FarmRepository farmRepository, FieldService fieldService) {
        this.farmRepository = farmRepository;
        this.fieldService = fieldService;
    }

    @Override
    public Farm save(@Valid Farm farm) {
        validateFarm(farm);
        fieldAreaSumCheck(farm);
        return farmRepository.save(farm);
    }

    @Override
    public Optional<Farm> findById(UUID id) {
        Optional<Farm> farm = farmRepository.findById(id);
        if (farm.isEmpty()) {
            throw new FarmNotFoundException(id);
        }
        return farm;
    }

    @Override
    public Page<Farm> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void delete(Farm farm) {
        if (farm.getFields() != null && !farm.getFields().isEmpty()) {
            for (Field field : farm.getFields()) {
                field.setFarm(null);
                fieldService.delete(field.getId());
            }
        }
        farmRepository.delete(farm);
    }
    @Transactional
    public Farm addFarmWithFields(Farm farm) {
        validateFarm(farm);
        fieldAreaSumCheck(farm);
        Farm savedFarm = farmRepository.save(farm);
        if (farm.getFields() != null) {
            for (Field field : farm.getFields()) {
                field.setFarm(savedFarm);
            }
            savedFarm.setFields(farm.getFields());
        }
        return farmRepository.save(savedFarm);
    }

    @Override
    public List<Farm> searchFarms(String name, String location, LocalDate creationDate) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidFarmException("Farm name cannot be empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new InvalidFarmException("Farm location cannot be empty.");
        }
        return farmRepository.findAll(
                Specification
                        .where(FarmSpecification.nameContains(name))
                        .and(FarmSpecification.locationContains(location))
                        .and(FarmSpecification.creationDateAfter(creationDate))
        );
    }
    @Transactional
    @Override
    public Farm updateFarm(UUID id, Farm updatedFarm) {
        Optional<Farm> existingFarmOpt = farmRepository.findById(id);

        if (!existingFarmOpt.isPresent()) {
            throw new FarmNotFoundException(id);
        }
        Farm existingFarm = existingFarmOpt.get();
        existingFarm.setName(updatedFarm.getName());
        existingFarm.setLocation(updatedFarm.getLocation());
        existingFarm.setArea(updatedFarm.getArea());
        existingFarm.setCreationDate(updatedFarm.getCreationDate());
        List<Field> existingFields = existingFarm.getFields();
        List<Field> updatedFields = updatedFarm.getFields();
        existingFields.removeIf(field -> !updatedFields.contains(field));
        for (Field field : updatedFields) {
            if (!existingFields.contains(field)) {
                field.setFarm(existingFarm);
                existingFields.add(field);
            }
        }

        existingFarm.setFields(existingFields);
        return farmRepository.save(existingFarm);
    }

    private void validateFarm(Farm farm) {
        if (farm.getName() == null || farm.getName().trim().isEmpty()) {
            throw new InvalidFarmException("Farm name is required.");
        }
        if (farm.getLocation() == null || farm.getLocation().trim().isEmpty()) {
            throw new InvalidFarmException("Farm location is required.");
        }
        if (farm.getArea() <= 0) {
            throw new InvalidFarmException("Farm area must be greater than 0.");
        }
        if (farm.getCreationDate() == null) {
            throw new InvalidFarmException("Creation date is required.");
        }
        if (farm.getCreationDate().isAfter(LocalDate.now())) {
            throw new InvalidFarmException("Creation date cannot be in the future.");
        }
    }


    private void fieldAreaSumCheck(Farm farm) {
        if (farm.getFields() != null && !farm.getFields().isEmpty()) {
            double totalFieldArea = farm.getFields().stream()
                    .mapToDouble(Field::getArea)
                    .sum();
            if (!farm.isValidArea(totalFieldArea)) {
                throw new InvalidFarmException("Total area of fields > the farm's available area.");
            }

            for (Field field : farm.getFields()) {
                if (field.getArea() <= 0) {
                    throw new InvalidFarmException("Field area must be more than 0.");
                }
            }
        }
    }
}
