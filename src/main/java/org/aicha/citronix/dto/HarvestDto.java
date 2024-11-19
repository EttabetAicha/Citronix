package org.aicha.citronix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HarvestDto {
    private UUID id;

    @NotNull(message = "Season is mandatory")
    private String season;

    @NotNull(message = "Harvest date is mandatory")
    private LocalDate harvestDate;

    @NotNull(message = "Total quantity is mandatory")
    @Positive(message = "Total quantity must be positive")
    private Double totalQuantity;

    @NotNull(message = "Tree ID is mandatory")
    private UUID treeId;
}