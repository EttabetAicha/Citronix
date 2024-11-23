package org.aicha.citronix.web.vm.request.tree;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class TreeCreateVM {
    @NotNull
    private LocalDate plantingDate;

    @NotNull
    private UUID fieldId;
}
