package org.aicha.citronix.web.reset;

import jakarta.validation.Valid;
import org.aicha.citronix.dto.FarmDto;
import org.aicha.citronix.dto.FieldDto;
import org.aicha.citronix.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fields")
@Validated
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @GetMapping
    public ResponseEntity<List<FieldDto>> getAllFields() {
        List<FieldDto> fields = fieldService.getAllFields();
        return ResponseEntity.ok(fields);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldDto> getFieldById(@PathVariable UUID id) {
        FieldDto field = fieldService.getFieldById(id);
        return ResponseEntity.ok(field);
    }

    @PostMapping
    public ResponseEntity<FieldDto> createField(@RequestParam UUID farmId, @Valid @RequestBody FieldDto fieldDto) {
        FieldDto createdField = fieldService.createField(fieldDto, farmId);
        return ResponseEntity.ok(createdField);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldDto> updateField(@PathVariable UUID id, @RequestParam UUID farmId, @Valid @RequestBody FieldDto fieldDto) {
        fieldDto.setId(id);
        FieldDto updatedField = fieldService.updateField(fieldDto, farmId);
        return ResponseEntity.ok(updatedField);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable UUID id) {
        fieldService.deleteField(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<FieldDto>> getFieldsByFarmId(@PathVariable UUID farmId) {
        List<FieldDto> fields = fieldService.findByFarmId(farmId);
        return ResponseEntity.ok(fields);
    }

    @PostMapping("/associate")
    public ResponseEntity<FarmDto> associateFieldsToFarm(@Valid @RequestBody FarmDto farmDto) {
        FarmDto updatedFarm = fieldService.associateFieldsToFarm(farmDto);
        return ResponseEntity.ok(updatedFarm);
    }
}