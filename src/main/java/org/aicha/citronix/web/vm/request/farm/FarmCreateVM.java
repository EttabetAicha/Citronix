package org.aicha.citronix.web.vm.request.farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FarmCreateVM {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Area is required")
    private Double area;

    @NotNull(message = "Creation date is required")
    private LocalDate creationDate;
}
