package org.aicha.citronix.web.reset;

import org.aicha.citronix.dto.HarvestDetailDTO;
import org.aicha.citronix.service.HarvestDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/harvest-details")
public class HarvestDetailController {

    private final HarvestDetailService harvestDetailService;

    public HarvestDetailController(HarvestDetailService harvestDetailService) {
        this.harvestDetailService = harvestDetailService;
    }

    @PostMapping
    public ResponseEntity<HarvestDetailDTO> createHarvestDetail(@RequestBody HarvestDetailDTO harvestDetailDTO) {
        HarvestDetailDTO result = harvestDetailService.createHarvestDetail(harvestDetailDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/harvest/{harvestId}")
    public ResponseEntity<List<HarvestDetailDTO>> getHarvestDetailsByHarvestId(@PathVariable UUID harvestId) {
        List<HarvestDetailDTO> result = harvestDetailService.getHarvestDetailsByHarvestId(harvestId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tree/{treeId}")
    public ResponseEntity<List<HarvestDetailDTO>> getHarvestDetailsByTreeId(@PathVariable UUID treeId) {
        List<HarvestDetailDTO> result = harvestDetailService.getHarvestDetailsByTreeId(treeId);
        return ResponseEntity.ok(result);
    }
}