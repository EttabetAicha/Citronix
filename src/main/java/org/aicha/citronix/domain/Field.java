package org.aicha.citronix.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @DecimalMin(value = "0.1", message = "The area must be at least 0.1 hectare")
    private Double area;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Tree> trees;
    @Override
    public String toString() {
        return "Field{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", farm=" + farm +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        return id != null ? id.equals(field.id) : field.id == null;
    }
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}