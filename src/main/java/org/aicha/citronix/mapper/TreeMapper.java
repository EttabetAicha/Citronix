package org.aicha.citronix.mapper;

import org.aicha.citronix.domain.Tree;
import org.aicha.citronix.dto.TreeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    @Mapping(source = "field.id", target = "fieldId")
    TreeDto toDto(Tree tree);

    @Mapping(target = "field", ignore = true)
    Tree toEntity(TreeDto treeDto);
}