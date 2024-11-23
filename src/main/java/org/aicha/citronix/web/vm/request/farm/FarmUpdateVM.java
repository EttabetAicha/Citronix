package org.aicha.citronix.web.vm.request.farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FarmUpdateVM {
    @NotNull(message = "ID is required")
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Area is required")
    private Double area;

    @NotNull(message = "Creation date is required")
    private LocalDate creationDate;
}
