package org.aicha.citronix.web.vm.request.farm;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class FarmDTO {
    private String location;
    private String name;
    private double area;
    private LocalDate creationDate;
    private List<ListField> fields;
}

