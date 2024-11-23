package org.aicha.citronix.service;

import org.aicha.citronix.domain.Tree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TreeService {

    Tree save(Tree tree);

    Optional<Tree> findById(UUID id);

    List<Tree> findAll();

    Page<Tree> findByFieldId(UUID fieldId, Pageable pageable);

    void delete(Tree tree);

    double calculateProductivity(UUID treeId);
}
