package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Integer> {

    @Query("SELECT t FROM Tree t WHERE t.field.id = :fieldId")
    List<Tree> findByFieldId(Integer fieldId);
}