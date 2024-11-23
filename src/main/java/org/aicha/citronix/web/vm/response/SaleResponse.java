package org.aicha.citronix.web.vm.response;

import lombok.Data;

import java.util.UUID;
@Data
public class SaleResponse {
    private UUID id;
    private java.time.LocalDate date;
    private double unitPrice;
    private String client;
    private double revenue;
    private double quantity;
    private UUID harvestId;


}