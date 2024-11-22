package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TreeRepository extends JpaRepository<Tree, UUID> {

    @Query("SELECT t FROM Tree t WHERE t.field.id = :fieldId")
    List<Tree> findByFieldId(UUID fieldId);
    Optional<Tree> findById(UUID id);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}