package org.aicha.citronix.service;

import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.Sale;
import org.aicha.citronix.dto.SaleDTO;
import org.aicha.citronix.mapper.SaleMapper;
import org.aicha.citronix.repository.HarvestRepository;
import org.aicha.citronix.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final SaleMapper saleMapper;

    public SaleService(SaleRepository saleRepository, HarvestRepository harvestRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.harvestRepository = harvestRepository;
        this.saleMapper = saleMapper;
    }

    public SaleDTO createSale(SaleDTO saleDTO) {
        Harvest harvest = harvestRepository.findById(saleDTO.getHarvestId())
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found with ID: " + saleDTO.getHarvestId()));

        Sale sale = new Sale();
        sale.setSaleDate(saleDTO.getSaleDate());
        sale.setUnitPrice(saleDTO.getUnitPrice());
        sale.setClientName(saleDTO.getClientName());
        sale.setQuantitySold(saleDTO.getQuantitySold());
        sale.setRevenue(sale.calculateRevenue(saleDTO.getQuantitySold()));
        sale.setHarvest(harvest);

        Sale savedSale = saleRepository.save(sale);
        return saleMapper.toDto(savedSale);
    }

    public List<SaleDTO> getSalesByHarvestId(UUID harvestId) {
            List<Sale> sales = saleRepository.findByHarvestId(harvestId);
        return saleMapper.toDto(sales);
    }
    public List<SaleDTO> getSalesByClientName(String clientName) {
        List<Sale> sales = saleRepository.findByClientName(clientName);
        return saleMapper.toDto(sales);
    }
}