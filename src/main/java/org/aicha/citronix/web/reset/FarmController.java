package org.aicha.citronix.web.reset;

import jakarta.validation.Valid;
import org.aicha.citronix.dto.FarmDto;
import org.aicha.citronix.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
@Validated
public class FarmController {

    @Autowired
    private FarmService farmService;

    @GetMapping
    public ResponseEntity<List<FarmDto>> getAllFarms() {
        List<FarmDto> farms = farmService.getAllFarms();
        return ResponseEntity.ok(farms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmDto> getFarmById(@PathVariable Integer id) {
        FarmDto farm = farmService.getFarmById(id);
        return ResponseEntity.ok(farm);
    }

    @PostMapping
    public ResponseEntity<FarmDto> createFarm(@Valid @RequestBody FarmDto farmDto) {
        FarmDto createdFarm = farmService.createFarm(farmDto);
        return ResponseEntity.ok(createdFarm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmDto> updateFarm(@PathVariable Integer id, @Valid @RequestBody FarmDto farmDto) {
        farmDto.setId(id);
        FarmDto updatedFarm = farmService.updateFarm(farmDto);
        return ResponseEntity.ok(updatedFarm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Integer id) {
        farmService.deleteFarm(id);
        return ResponseEntity.noContent().build();
    }
}