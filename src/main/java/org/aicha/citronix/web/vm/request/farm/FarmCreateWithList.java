package org.aicha.citronix.web.vm.request.farm;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.aicha.citronix.domain.Field;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FarmCreateWithList {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Area is required")
    private Double area;

    @NotNull(message = "Creation date is required")
    private LocalDate creationDate;

    @NotBlank
    private List<Field> fields;
}
