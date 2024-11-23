package org.aicha.citronix.service.imp;

import org.aicha.citronix.domain.HarvestDetail;
import org.aicha.citronix.repository.HarvestDetailRepository;
import org.aicha.citronix.repository.HarvestRepository;
import org.aicha.citronix.repository.TreeRepository;
import org.aicha.citronix.web.vm.request.harvest.HarvestDetailCreateVM;
import org.aicha.citronix.web.vm.response.harvest.HarvestDetailResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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
        HarvestDetail harvestDetail = new HarvestDetail();
        harvestDetail.setHarvest(harvestRepository.findById(request.getHarvestId())
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found with ID: " + request.getHarvestId())));
        harvestDetail.setTree(treeRepository.findById(request.getTreeId())
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with ID: " + request.getTreeId())));
        harvestDetail.setQuantity(request.getQuantity());

        HarvestDetail savedDetail = harvestDetailRepository.save(harvestDetail);
        return new HarvestDetailResponse(
                savedDetail.getId(),
                savedDetail.getHarvest().getId(),
                savedDetail.getTree().getId(),
                savedDetail.getQuantity()
        );
    }

    public List<HarvestDetailResponse> getHarvestDetailsByHarvestId(UUID harvestId) {
        return harvestDetailRepository.findByHarvestId(harvestId).stream()
                .map(detail -> new HarvestDetailResponse(
                        detail.getId(),
                        detail.getHarvest().getId(),
                        detail.getTree().getId(),
                        detail.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    public List<HarvestDetailResponse> getHarvestDetailsByTreeId(UUID treeId) {
        return harvestDetailRepository.findByTreeId(treeId).stream()
                .map(detail -> new HarvestDetailResponse(
                        detail.getId(),
                        detail.getHarvest().getId(),
                        detail.getTree().getId(),
                        detail.getQuantity()
                ))
                .collect(Collectors.toList());
    }
}