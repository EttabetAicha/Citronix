package org.aicha.citronix.repository;


import org.aicha.citronix.domain.Tree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TreeRepository extends JpaRepository<Tree, UUID> {
    Page<Tree> findByFieldId(UUID fieldId, Pageable pageable);

    List<Tree> findByFieldId(UUID fieldId);

}
