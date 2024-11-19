package org.aicha.citronix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeDto {

    private UUID id;

    @NotNull(message = "Plantation date cannot be null")
    @PastOrPresent(message = "Plantation date must be in the past or present")
    private LocalDate plantationDate;

    @NotNull(message = "Field ID cannot be null")
    private UUID fieldId;
}