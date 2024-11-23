package org.aicha.citronix.web.mapper.request;


import org.aicha.citronix.domain.Tree;
import org.aicha.citronix.web.vm.request.tree.TreeCreateVM;
import org.aicha.citronix.web.vm.response.tree.TreeResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    @Mapping(target = "field.id", source = "fieldId")
    Tree toEntity(TreeCreateVM treeCreateVM);

    @Mapping(target = "age", expression = "java(tree.calculateAge())")
    @Mapping(target = "productivity", expression = "java(tree.calculateProductivity())")
    TreeResponseVM toResponseVM(Tree tree);
}
