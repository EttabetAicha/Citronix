package org.aicha.citronix.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Field {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @Min(value = 1000, message = "Area must be at least 1000 m²")
    private double area;

    @ManyToOne(optional = false)
    private Farm farm;

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    private List<Tree> trees;

    public boolean isTreeDensityValid(int numberOfTrees) {
        return numberOfTrees <= area * 10;
    }

    public boolean isAreaValid() {
        return area < (farm.getArea() * 0.5);
    }
}