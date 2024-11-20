package org.aicha.citronix.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Size(min = 2, max = 200)
    private String location;

    @NotNull
    private Double area;
    @Column(name = "creation_date", nullable = false)
    @NotNull
    private LocalDate creationDate;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Field> fields;

    public double getTotalArea() {
        return fields.stream().mapToDouble(Field::getArea).sum();
    }
}
