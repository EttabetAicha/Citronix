package org.aicha.citronix.mapper;

import org.aicha.citronix.domain.Field;
import org.aicha.citronix.dto.FieldDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    FieldDto toDto(Field field);
    Field toEntity(FieldDto FieldDto);


}
