package org.aicha.citronix.web.vm.request.farm;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class FarmSearchVM {

    public String name;
    public String location;
    public LocalDate date;

    public FarmSearchVM(String name, String location, LocalDate date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

}
