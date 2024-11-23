package org.aicha.citronix.web.rest;


import org.aicha.citronix.domain.Sale;
import org.aicha.citronix.service.imp.SaleServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales")
public class SaleController {


    private final SaleServiceImpl saleService;

    public SaleController(SaleServiceImpl saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public Sale createSale(@RequestBody Sale sale) {
        System.out.println(sale);
        return saleService.createSale(sale);
    }
}
