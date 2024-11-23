package org.aicha.citronix.web.vm.response.harvest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class HarvestDetailResponse {
    private UUID id;
    private UUID harvestId;
    private UUID treeId;
    private double quantity;
}
