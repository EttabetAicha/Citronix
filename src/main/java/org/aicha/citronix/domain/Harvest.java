package org.aicha.citronix.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.aicha.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Season season;

    @NotNull
    private LocalDate harvestDate;

    @NotNull
    @Positive
    private Double totalQuantity;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<Sale> sales;
}