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
        if (!farm.isPresent()) {
            throw new FarmNotFoundException(id);
        }
        return farm;
    }

    @Override
    public Page<Farm> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable);
    }

    @Override
    public void delete(Farm farm) {
        if (farm.getFields() != null && !farm.getFields().isEmpty()) {
            for (Field field : farm.getFields()) {
                fieldService.delete(field.getId());
            }
        }
        farmRepository.delete(farm);
    }



    @Override
    public List<Farm> searchFarms(String name, String location, LocalDate startDate) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidFarmException("Farm name cannot be empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new InvalidFarmException("Farm location cannot be empty.");
        }
        if (startDate == null) {
            throw new InvalidFarmException("Start date cannot be null.");
        }

        return farmRepository.findAll(
                Specification
                        .where(FarmSpecification.nameContains(name))
                        .and(FarmSpecification.locationContains(location))
                        .and(FarmSpecification.creationDateAfter(startDate))
        );
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
