package org.aicha.citronix.service.imp;

import org.aicha.citronix.domain.Field;
import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.HarvestDetail;
import org.aicha.citronix.domain.Tree;
import org.aicha.citronix.domain.enums.Season;
import org.aicha.citronix.repository.HarvestDetailRepository;
import org.aicha.citronix.repository.HarvestRepository;
import org.aicha.citronix.service.HarvestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HarvestServiceImpl implements HarvestService {

    private HarvestRepository harvestRepository;
    private HarvestDetailRepository harvestDetailRepository;

    public HarvestServiceImpl(HarvestRepository harvestRepository, HarvestDetailRepository harvestDetailRepository) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailRepository = harvestDetailRepository;
    }

    @Transactional
    public Harvest save(Harvest harvest) {
        validateFieldsForHarvest(harvest);
        for (Field field : harvest.getFarm().getFields()) {
            if (field.getTrees() != null) {
                for (Tree tree : field.getTrees()) {
                    validateTreeForHarvest(tree, harvest.getSeason());
                }
            }
        }
        Harvest savedHarvest = harvestRepository.save(harvest);
        for (Field field : harvest.getFarm().getFields()) {
            if (field.getTrees() != null) {
                for (Tree tree : field.getTrees()) {
                    double quantity = tree.calculateProductivity();
                    HarvestDetail harvestDetail = new HarvestDetail();
                    harvestDetail.setQuantity(quantity);
                    harvestDetail.setTree(tree);
                    harvestDetail.setHarvest(savedHarvest);
                    harvestDetailRepository.save(harvestDetail);
                }
            }
        }

        return savedHarvest;
    }
    private void validateFieldsForHarvest(Harvest harvest) {
        for (Field field : harvest.getFarm().getFields()) {
            List<Harvest> existingHarvests = harvestRepository.findByFarmAndSeason(harvest.getFarm(), harvest.getSeason());
            for (Harvest existingHarvest : existingHarvests) {
                if (existingHarvest.getFarm().getFields().contains(field)) {
                    throw new IllegalStateException("Field has already been harvested in this season.");
                }
            }
        }
    }
    private void validateTreeForHarvest(Tree tree, Season season) {
        List<HarvestDetail> existingHarvestDetails = harvestDetailRepository.findByTreeAndSeason(tree, season);
        if (!existingHarvestDetails.isEmpty()) {
            throw new IllegalStateException("Tree has already been harvested in this season.");
        }
    }
    private void validateHarvestForFarm(Harvest harvest) {
        List<Harvest> existingHarvests = harvestRepository.findByFarmAndSeason(harvest.getFarm(), harvest.getSeason());
        if (!existingHarvests.isEmpty()) {
            throw new IllegalStateException("This farm has already been harvested in the same season.");
        }
    }

    private void validateHarvestDetails(Harvest harvest) {
        List<Harvest> existingHarvests = harvestRepository.findByFarmAndSeason(harvest.getFarm(), harvest.getSeason());
        if (!existingHarvests.isEmpty()) {
            throw new IllegalStateException("Harvest already exists for this farm in the selected season.");
        }
    }
    @Override
    public Optional<Harvest> findById(UUID id) {
        return harvestRepository.findById(id);
    }

    @Override
    public List<Harvest> findAll() {
        return harvestRepository.findAll();
    }

    @Override
    public List<Harvest> findByFarmId(UUID farmId) {
        return harvestRepository.findByFarmId(farmId);
    }

    @Override
    public void delete(Harvest harvest) {
        harvestRepository.delete(harvest);
    }

    @Override
    public boolean isSeasonAvailable(UUID farmId, String season) {
        return harvestRepository.findByFarmIdAndSeason(farmId, Season.valueOf(season)).isEmpty();
    }

    @Override
    public List<Harvest> getHarvestsBySeason(Season season) {
        return harvestRepository.findBySeason(season);
    }
}
