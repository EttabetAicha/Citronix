package org.aicha.citronix.web.reset;

import jakarta.validation.Valid;
import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.enums.Season;
import org.aicha.citronix.service.HarvestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/harvests")
public class HarvestController {

    private final HarvestService harvestService;

    public HarvestController(HarvestService harvestService) {
        this.harvestService = harvestService;
    }

    @PostMapping
    public ResponseEntity<Harvest> createHarvest(@Valid @RequestBody Harvest harvest) {
        Harvest savedHarvest = harvestService.save(harvest);
        return ResponseEntity.ok(savedHarvest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Harvest> getHarvestById(@PathVariable UUID id) {
        Optional<Harvest> harvest = harvestService.findById(id);
        return harvest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Harvest>> getAllHarvests() {
        List<Harvest> harvests = harvestService.findAll();
        return ResponseEntity.ok(harvests);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable UUID id) {
        Optional<Harvest> harvest = harvestService.findById(id);
        if (harvest.isPresent()) {
            harvestService.delete(harvest.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/season/{season}")
    public ResponseEntity<List<Harvest>> getHarvestsBySeason(@PathVariable Season season) {
        List<Harvest> harvests = harvestService.getHarvestsBySeason(season);
        return ResponseEntity.ok(harvests);
    }
}