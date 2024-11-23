package org.aicha.citronix.web.mapper.request;


import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.web.vm.request.farm.FarmCreateVM;
import org.aicha.citronix.web.vm.request.farm.FarmUpdateVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(target = "id", ignore = true)
    Farm toEntity(FarmCreateVM createFarmVM);

    Farm toEntity(FarmUpdateVM updateFarmVM);

    FarmCreateVM toCreateVM(Farm farm);

    FarmUpdateVM toUpdateVM(Farm farm);

}
