package org.aicha.citronix.web.mapper.request;

import jakarta.validation.Valid;
import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.web.vm.request.harvest.HarvestCreateVM;
import org.aicha.citronix.web.vm.response.harvest.HarvestResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    @Mapping(target = "farm.id", source = "farmId")
    @Mapping(target = "season", expression = "java(com.spring.citronix.domain.enums.Season.valueOf(harvestCreateVM.getSeason()))")
    Harvest toEntity(@Valid HarvestCreateVM harvestCreateVM);

    @Mapping(target = "season", expression = "java(harvest.getSeason().name())")
    HarvestResponseVM toResponseVM(Harvest harvest);
}
