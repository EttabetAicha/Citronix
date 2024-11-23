package org.aicha.citronix.web.vm.request.field;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TreeDensityValidationVM {

    @NotNull(message = "Field ID is required")
    private UUID fieldId;

    @NotNull(message = "Number of trees is required")
    private Integer numberOfTrees;
}
