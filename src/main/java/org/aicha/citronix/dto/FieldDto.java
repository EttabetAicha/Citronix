package org.aicha.citronix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FieldDto {
    private Integer id;
    @NotNull(message = "Area is mandatory")
    @Positive(message = "Area must be positive")
    private Double area;
    @NotNull
    private Integer farmId;



}
