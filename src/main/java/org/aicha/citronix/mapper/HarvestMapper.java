package org.aicha.citronix.mapper;

import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.dto.HarvestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HarvestMapper {


    HarvestDto toDto(Harvest harvest);
    Harvest toEntity(HarvestDto harvestDto);
}