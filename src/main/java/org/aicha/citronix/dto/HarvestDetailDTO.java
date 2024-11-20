package org.aicha.citronix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailDTO {

    @NotNull(message = "Harvest ID cannot be null")
    private UUID harvestId;

    @NotNull(message = "Tree ID cannot be null")
    private UUID treeId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private Double quantity;
}