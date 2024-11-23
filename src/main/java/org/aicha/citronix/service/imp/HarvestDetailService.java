package org.aicha.citronix.service.imp;

import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.HarvestDetail;
import org.aicha.citronix.domain.Tree;
import org.aicha.citronix.repository.HarvestDetailRepository;
import org.aicha.citronix.repository.HarvestRepository;
import org.aicha.citronix.repository.TreeRepository;
import org.aicha.citronix.web.vm.request.harvest.HarvestDetailCreateVM;
import org.aicha.citronix.web.vm.response.harvest.HarvestDetailResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class HarvestDetailService {

    private final HarvestDetailRepository harvestDetailRepository;
    private final HarvestRepository harvestRepository;
    private final TreeRepository treeRepository;

    public HarvestDetailService(HarvestDetailRepository harvestDetailRepository, HarvestRepository harvestRepository, TreeRepository treeRepository) {
        this.harvestDetailRepository = harvestDetailRepository;
        this.harvestRepository = harvestRepository;
        this.treeRepository = treeRepository;
    }

    public HarvestDetailResponse createHarvestDetail(HarvestDetailCreateVM request) {
        Harvest harvest = harvestRepository.findById(request.getHarvestId())
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found with ID: " + request.getHarvestId()));

        Tree tree = treeRepository.findById(request.getTreeId())
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with ID: " + request.getTreeId()));
        HarvestDetail harvestDetail = new HarvestDetail();
        harvestDetail.setHarvest(harvest);
        harvestDetail.setTree(tree);
        harvestDetail.setQuantity(request.getQuantity());

        HarvestDetail savedDetail = harvestDetailRepository.save(harvestDetail);
        return new HarvestDetailResponse(
                savedDetail.getId(),
                savedDetail.getHarvest().getId(),
                savedDetail.getTree().getId(),
                savedDetail.getQuantity()
        );
    }

    public List<HarvestDetail> getHarvestDetailsByHarvestId(UUID harvestId) {
        return harvestDetailRepository.findByHarvestId(harvestId);
    }
    public List<HarvestDetail> getHarvestDetailsByTreeId(UUID treeId) {
        return harvestDetailRepository.findByTreeId(treeId);
    }
}
