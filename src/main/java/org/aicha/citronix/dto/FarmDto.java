package org.aicha.citronix.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmDto {
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotNull(message = "Creation date is mandatory")
    private LocalDate creationDate;

    @NotNull(message = "Area is mandatory")
    @Positive(message = "Area must be positive")
    private Double area;
    private List<FieldDto> fields;
}