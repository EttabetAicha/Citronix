package org.aicha.citronix.domain;

import jakarta.persistence.*;
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
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Season season;

    @Column(nullable = false)
    private LocalDate harvestDate;

    @Column(nullable = false)
    private double totalQuantity;

    @ManyToOne
    private Tree tree;

    @OneToMany(mappedBy = "harvest",fetch = FetchType.EAGER)
    private List<HarvestDetail> harvestDetails;

    @OneToMany(mappedBy = "harvest")
    private List<Sale> sales;
}