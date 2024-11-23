package org.aicha.citronix.domain;

import jakarta.persistence.*;
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
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private double quantity;

    @ManyToOne(optional = false)
    private Tree tree;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    private Harvest harvest;
}
