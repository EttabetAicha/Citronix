package org.aicha.citronix.mapper;

import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.dto.HarvestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    @Mapping(source = "tree.id", target = "treeId")
    HarvestDto toDto(Harvest harvest);

    @Mapping(source = "treeId", target = "tree.id")
    Harvest toEntity(HarvestDto harvestDto);
}