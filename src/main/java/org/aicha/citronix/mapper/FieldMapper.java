package org.aicha.citronix.mapper;

import org.aicha.citronix.domain.Field;
import org.aicha.citronix.dto.FieldDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    @Mapping(source = "farm.id", target = "farmId")
    FieldDto toDto(Field field);
    @Mapping(source = "farmId", target = "farm.id")
    Field toEntity(FieldDto FieldDto);


}
