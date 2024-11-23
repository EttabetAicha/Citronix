package org.aicha.citronix.web.vm.request.field;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FieldCreateVM {

    @NotNull(message = "Field area is required")
    private Double area;

    @NotNull(message = "Farm ID is required")
    private UUID farmId;
}
