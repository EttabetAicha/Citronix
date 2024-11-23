package org.aicha.citronix.service.imp;

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

    private final HarvestRepository harvestRepository;
    private final HarvestDetailRepository harvestDetailRepository;

    public HarvestServiceImpl(HarvestRepository harvestRepository, HarvestDetailRepository harvestDetailRepository) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailRepository = harvestDetailRepository;
    }

    @Transactional
    public Harvest save(Harvest harvest) {
        validateTreeForHarvest(harvest.getTree(), harvest.getSeason());
        double quantity = harvest.getTree().calculateProductivity();
        harvest.setTotalQuantity(quantity);
        Harvest savedHarvest = harvestRepository.save(harvest);

        HarvestDetail harvestDetail = new HarvestDetail();
        harvestDetail.setQuantity(quantity);
        harvestDetail.setTree(harvest.getTree());
        harvestDetail.setHarvest(savedHarvest);
        harvestDetailRepository.save(harvestDetail);

        return savedHarvest;
    }

    private void validateTreeForHarvest(Tree tree, Season season) {
        List<HarvestDetail> existingHarvestDetails = harvestDetailRepository.findByTreeAndSeason(tree, season);
        if (!existingHarvestDetails.isEmpty()) {
            throw new IllegalStateException("Tree has already been harvested in this season.");
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
    public List<Harvest> findByTreeId(UUID treeId) {
        return harvestRepository.findByTreeId(treeId);
    }

    @Override
    public void delete(Harvest harvest) {
        harvestRepository.delete(harvest);
    }

    @Override
    public boolean isSeasonAvailable(UUID treeId, String season) {
        return harvestRepository.findByTreeIdAndSeason(treeId, Season.valueOf(season)).isEmpty();
    }

    @Override
    public List<Harvest> getHarvestsBySeason(Season season) {
        return harvestRepository.findBySeason(season);
    }
}