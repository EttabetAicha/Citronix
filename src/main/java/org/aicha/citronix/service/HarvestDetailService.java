package org.aicha.citronix.service;

import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.HarvestDetail;
import org.aicha.citronix.domain.Tree;
import org.aicha.citronix.dto.HarvestDetailDTO;
import org.aicha.citronix.mapper.HarvestDetailMapper;
import org.aicha.citronix.repository.HarvestDetailRepository;
import org.aicha.citronix.repository.HarvestRepository;
import org.aicha.citronix.repository.TreeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HarvestDetailService {

    private final HarvestDetailRepository harvestDetailRepository;
    private final HarvestRepository harvestRepository;
    private final TreeRepository treeRepository;
    private final HarvestDetailMapper harvestDetailMapper;

    public HarvestDetailService(HarvestDetailRepository harvestDetailRepository, HarvestRepository harvestRepository, TreeRepository treeRepository, HarvestDetailMapper harvestDetailMapper) {
        this.harvestDetailRepository = harvestDetailRepository;
        this.harvestRepository = harvestRepository;
        this.treeRepository = treeRepository;
        this.harvestDetailMapper = harvestDetailMapper;
    }

    public HarvestDetailDTO createHarvestDetail(HarvestDetailDTO request) {
        Harvest harvest = harvestRepository.findById(request.getHarvestId())
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found with ID: " + request.getHarvestId()));

        Tree tree = treeRepository.findById(request.getTreeId())
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with ID: " + request.getTreeId()));

        HarvestDetail harvestDetail = new HarvestDetail();
        harvestDetail.setHarvest(harvest);
        harvestDetail.setTree(tree);
        harvestDetail.setQuantity(request.getQuantity());

        HarvestDetail savedDetail = harvestDetailRepository.save(harvestDetail);

        return harvestDetailMapper.toDto(savedDetail);
    }

    public List<HarvestDetailDTO> getHarvestDetailsByHarvestId(UUID harvestId) {
        List<HarvestDetail> harvestDetails = harvestDetailRepository.findByHarvestId(harvestId);
        return harvestDetailMapper.toDto(harvestDetails);
    }

    public List<HarvestDetailDTO> getHarvestDetailsByTreeId(UUID treeId) {
        List<HarvestDetail> harvestDetails = harvestDetailRepository.findByTreeId(treeId);
        return harvestDetailMapper.toDto(harvestDetails);
    }
}