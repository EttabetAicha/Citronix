package org.aicha.citronix.web.vm.response.tree;

import lombok.Data;
import org.aicha.citronix.domain.Field;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class TreeResponseVM {
    private UUID id;
    private LocalDate plantingDate;
    private int age;
    private double productivity;
    private Field field;
}
