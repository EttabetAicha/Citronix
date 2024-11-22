package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {
    @Query("SELECT f FROM Field f WHERE f.farm.id = :farmId")
    List<Field> findByFarmId(UUID farmId);
    Optional<Field> findById(UUID id);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}