package org.aicha.citronix.web.vm.request.harvest;

import lombok.Data;

import java.util.UUID;

@Data
public class HarvestDetailCreateVM {
    private UUID harvestId;
    private UUID treeId;
    private double quantity;
}
