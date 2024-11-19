package org.aicha.citronix.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class HarvestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotNull
    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "tree_id")
    private Tree tree;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    private Harvest harvest;
}
