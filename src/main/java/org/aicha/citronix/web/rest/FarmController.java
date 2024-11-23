package org.aicha.citronix.web.rest;


import jakarta.validation.Valid;
import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.domain.Field;
import org.aicha.citronix.service.FarmService;
import org.aicha.citronix.web.mapper.request.FarmMapper;
import org.aicha.citronix.web.vm.request.farm.FarmSearchVM;
import org.aicha.citronix.web.vm.request.farm.FarmUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/farms")
public class FarmController {

    private final FarmService farmService;
    private final FarmMapper farmMapper;

    public FarmController(FarmService farmService, FarmMapper farmMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }

    @PostMapping("/create")
    public Farm createFarm(@RequestBody @Valid Farm farmDTO) {

        if (farmDTO.getFields() != null) {
            List<Field> fields = farmDTO.getFields().stream().map(listField -> {
                Field field = new Field();
                field.setArea(listField.getArea());
                return field;
            }).collect(Collectors.toList());

            farmDTO.setFields(fields);
        }

        return farmService.save(farmDTO);
    }
    @PostMapping("/createWithFields")
    public Farm createFarmWithFields(@RequestBody @Valid Farm farmDTO) {
        if (farmDTO.getFields() != null) {
            List<Field> fields = farmDTO.getFields().stream().map(listField -> {
                Field field = new Field();
                field.setArea(listField.getArea());
                field.setFarm(farmDTO);
                return field;
            }).collect(Collectors.toList());

            farmDTO.setFields(fields);
        }

        return farmService.addFarmWithFields(farmDTO);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Farm> findById(@PathVariable UUID id) {
        return farmService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Page<Farm> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return farmService.findAll(pageable);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFarm(@PathVariable UUID id) {
        Optional<Farm> farm = farmService.findById(id);
        if (farm.isPresent()) {
            farmService.delete(farm.get());
            return ResponseEntity.ok("Farm deleted successfully.");
        }
        return ResponseEntity.status(404).body("Farm not found.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Farm> updateFarm(@PathVariable UUID id, @RequestBody FarmUpdateVM farmUpdateVM) {
        Optional<Farm> existingFarmOpt = farmService.findById(id);

        if (!existingFarmOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Farm existingFarm = existingFarmOpt.get();
        existingFarm.setName(farmUpdateVM.getName());
        existingFarm.setLocation(farmUpdateVM.getLocation());
        existingFarm.setArea(farmUpdateVM.getArea());

        if (farmUpdateVM.getFields() != null) {
            // Clear the existing fields and add the new ones
            existingFarm.getFields().clear();
            for (Field field : farmUpdateVM.getFields()) {
                field.setFarm(existingFarm); // Set the farm for each field
                existingFarm.getFields().add(field);
            }
        }

        Farm savedFarm = farmService.save(existingFarm);
        return ResponseEntity.ok(savedFarm);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Farm>> searchFarm(@RequestBody @Valid FarmSearchVM farmSearch) {
        List<Farm> farms = farmService.searchFarms(
                farmSearch.getName(),
                farmSearch.getLocation(),
                farmSearch.getCreationDate()
        );
        return ResponseEntity.ok(farms);
    }
}
