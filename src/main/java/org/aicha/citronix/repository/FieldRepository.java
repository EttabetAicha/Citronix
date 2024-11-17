package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {

    @Query("SELECT f FROM Field f WHERE f.farm.id = :farmId")
    List<Field> findByFarmId(Integer farmId);
}