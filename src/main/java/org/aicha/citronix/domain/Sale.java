package org.aicha.citronix.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotNull
    private LocalDate saleDate;

    @NotNull
    private Double unitPrice;

    @NotNull
    private String clientName;

    @NotNull
    private Double quantitySold;

    @NotNull
    private Double revenue;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    private Harvest harvest;
}