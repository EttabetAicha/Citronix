package org.aicha.citronix.service.imp;


import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.Sale;
import org.aicha.citronix.repository.HarvestRepository;
import org.aicha.citronix.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaleServiceImpl {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;

    public SaleServiceImpl(SaleRepository saleRepository, HarvestRepository harvestRepository) {
        this.saleRepository = saleRepository;
        this.harvestRepository = harvestRepository;
    }

    public Sale createSale(Sale sale) {

        System.out.println(sale.getClass());

        if (sale.getHarvest().getId() == null) {
            throw new IllegalArgumentException("Harvest cannot be null.");
        }
        Optional<Harvest> harvestOpt = harvestRepository.findById(sale.getHarvest().getId());

        if (harvestOpt.isEmpty()) {
            throw new IllegalArgumentException("Harvest not found.");
        }
        sale.setHarvest(harvestOpt.get());

        sale.setRevenue(sale.calculateRevenue(sale.getQuantity()));
        return saleRepository.save(sale);
    }

}
