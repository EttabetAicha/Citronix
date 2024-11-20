package org.aicha.citronix.service;

import jakarta.validation.Valid;
import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.enums.Season;
import org.aicha.citronix.repository.HarvestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HarvestService {

    private final HarvestRepository harvestRepository;

    public HarvestService(HarvestRepository harvestRepository) {
        this.harvestRepository = harvestRepository;
    }

    public Harvest save(@Valid Harvest harvest) {
        if (!isSeasonAvailable(harvest.getSeason().toString())) {
            throw new org.aicha.citronix.exception.CustomException("A harvest for this season already exists.");
        }
        return harvestRepository.save(harvest);
    }

    public Optional<Harvest> findById(UUID id) {
        return harvestRepository.findById(id);
    }

    public List<Harvest> findAll() {
        return harvestRepository.findAll();
    }

    public void delete(Harvest harvest) {
        harvestRepository.delete(harvest);
    }

    public boolean isSeasonAvailable(String season) {
        return harvestRepository.findBySeason(Season.valueOf(season)).isEmpty();
    }

    public List<Harvest> getHarvestsBySeason(Season season) {
        return harvestRepository.findBySeason(season);
    }
}