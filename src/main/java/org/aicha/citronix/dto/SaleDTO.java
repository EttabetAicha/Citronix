package org.aicha.citronix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {

    private UUID id;
    private LocalDate saleDate;
    private Double unitPrice;
    private String clientName;
    private Double quantitySold;
    private Double revenue;
    private UUID harvestId;
}