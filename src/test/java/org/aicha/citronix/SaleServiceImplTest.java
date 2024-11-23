package org.aicha.citronix.service.imp;

import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.Sale;
import org.aicha.citronix.repository.HarvestRepository;
import org.aicha.citronix.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SaleServiceImplTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private HarvestRepository harvestRepository;

    @InjectMocks
    private SaleServiceImpl saleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSale_shouldThrowException_whenHarvestIsNull() {
        Sale sale = new Sale();
        sale.setHarvest(null);

        assertThrows(IllegalArgumentException.class, () -> saleService.createSale(sale), "Harvest cannot be null.");
    }

    @Test
    void createSale_shouldThrowException_whenHarvestIdIsNull() {
        Sale sale = new Sale();
        sale.setHarvest(new Harvest());

        assertThrows(IllegalArgumentException.class, () -> saleService.createSale(sale), "Harvest cannot be null.");
    }

    @Test
    void createSale_shouldThrowException_whenHarvestNotFound() {
        Sale sale = new Sale();
        Harvest harvest = new Harvest();
        harvest.setId(UUID.randomUUID());
        sale.setHarvest(harvest);

        when(harvestRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> saleService.createSale(sale), "Harvest not found.");
    }

    @Test
    void createSale_shouldThrowException_whenHarvestAlreadySold() {
        Sale sale = new Sale();
        Harvest harvest = new Harvest();
        harvest.setId(UUID.randomUUID());
        sale.setHarvest(harvest);

        when(harvestRepository.findById(any(UUID.class))).thenReturn(Optional.of(harvest));
        when(saleRepository.existsByHarvestId(any(UUID.class))).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> saleService.createSale(sale), "Harvest is already sold.");
    }

    @Test
    void createSale_shouldSaveSale_whenValidHarvest() {
        Sale sale = new Sale();
        Harvest harvest = new Harvest();
        harvest.setId(UUID.randomUUID());
        sale.setHarvest(harvest);
        sale.setQuantity(10);

        when(harvestRepository.findById(any(UUID.class))).thenReturn(Optional.of(harvest));
        when(saleRepository.existsByHarvestId(any(UUID.class))).thenReturn(false);
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);

        Sale savedSale = saleService.createSale(sale);

        // Add assertions to verify the saved sale
    }
}