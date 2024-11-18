package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer>, JpaSpecificationExecutor<Farm> {
    @Query("SELECT f FROM Farm f WHERE f.name = :name AND f.location = :location")
    List<Farm> findByNameAndLocation(@Param("name") String name, @Param("location") String location);
}