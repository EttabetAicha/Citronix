package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {

    @Query("SELECT f FROM Farm f WHERE f.name LIKE %:name% AND f.location LIKE %:location%")
    List<Farm> findByNameAndLocation(String name, String location);
}