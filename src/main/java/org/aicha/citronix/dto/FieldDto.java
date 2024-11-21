package org.aicha.citronix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class FieldDto {
    private UUID id;
    @NotNull(message = "Area is mandatory")
    @Positive(message = "Area must be positive")
    private Double area;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView(View.Summary.class)
    private UUID farmId;
    @NotNull(message = "Name is mandatory")
    private String name;
}