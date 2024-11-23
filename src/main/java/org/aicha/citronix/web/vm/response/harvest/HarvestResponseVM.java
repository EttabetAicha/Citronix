package org.aicha.citronix.web.vm.response.harvest;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Setter
@Getter
public class HarvestResponseVM {
    private UUID id;
    private String season;
    private LocalDate harvestDate;
    private double totalQuantity;
    private UUID farmId;
}
