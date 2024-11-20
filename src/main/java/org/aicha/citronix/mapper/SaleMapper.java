package org.aicha.citronix.mapper;

import org.aicha.citronix.domain.Sale;
import org.aicha.citronix.dto.SaleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "harvest.id", target = "harvestId")
    SaleDTO toDto(Sale sale);

    List<SaleDTO> toDto(List<Sale> sales);

    @Mapping(source = "harvestId", target = "harvest.id")
    Sale toEntity(SaleDTO saleDTO);
}