package org.aicha.citronix.web.vm.request.harvest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class HarvestCreateVM {
    @NotNull
    private String season;

    @NotNull
    private LocalDate harvestDate;

    @NotNull
    private double totalQuantity;

    @NotNull
    private UUID farmId;
}
