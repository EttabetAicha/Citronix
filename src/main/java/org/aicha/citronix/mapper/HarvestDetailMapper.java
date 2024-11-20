package org.aicha.citronix.mapper;

import org.aicha.citronix.domain.HarvestDetail;
import org.aicha.citronix.dto.HarvestDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper {

    @Mapping(source = "harvest.id", target = "harvestId")
    @Mapping(source = "tree.id", target = "treeId")
    HarvestDetailDTO toDto(HarvestDetail harvestDetail);

    List<HarvestDetailDTO> toDto(List<HarvestDetail> harvestDetails);

    @Mapping(source = "harvestId", target = "harvest.id")
    @Mapping(source = "treeId", target = "tree.id")
    HarvestDetail toEntity(HarvestDetailDTO harvestDetailDTO);
}