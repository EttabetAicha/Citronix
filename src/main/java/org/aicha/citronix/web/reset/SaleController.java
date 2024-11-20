package org.aicha.citronix.web.reset;

import org.aicha.citronix.dto.SaleDTO;
import org.aicha.citronix.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
        SaleDTO result = saleService.createSale(saleDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/harvest/{harvestId}")
    public ResponseEntity<List<SaleDTO>> getSalesByHarvestId(@PathVariable UUID harvestId) {
        List<SaleDTO> result = saleService.getSalesByHarvestId(harvestId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/client/{clientName}")
    public ResponseEntity<List<SaleDTO>> getSalesByClientName(@PathVariable String clientName) {
        List<SaleDTO> result = saleService.getSalesByClientName(clientName);
        return ResponseEntity.ok(result);
    }

}