package org.aicha.citronix.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmDto {
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotNull(message = "Area is mandatory")
    @Positive(message = "Area must be positive")
    private Double area;
}