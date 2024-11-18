package org.aicha.citronix.mapper;

import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.dto.FarmDto;
import org.aicha.citronix.web.vm.FarmVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    FarmDto toDto(Farm farm);
    Farm toEntity(FarmDto farmDto);
    FarmVM toVm(Farm farm);

}
