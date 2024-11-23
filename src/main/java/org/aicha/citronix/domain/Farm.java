package org.aicha.citronix.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Farm name is required")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Farm location is required")
    private String location;

    @Column(nullable = false)
    @Positive(message = "Farm area must be greater than zero")
    private double area;

    @Column(nullable = false)
    @PastOrPresent(message = "Creation date cannot be in the future")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Field> fields= new ArrayList<>();

    public boolean isValidArea(double fieldAreaSum) {
        return fieldAreaSum < this.area;
    }
}
