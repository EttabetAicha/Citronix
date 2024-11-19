package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer>, JpaSpecificationExecutor<Farm> {
    @Query("SELECT f FROM Farm f WHERE f.name = :name AND f.location = :location")
    List<Farm> findByNameAndLocation(@Param("name") String name, @Param("location") String location);
    Optional<Farm> findById(UUID id);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}