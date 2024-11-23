package org.aicha.citronix.repository;


import org.aicha.citronix.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID>, JpaSpecificationExecutor<Farm> {
    Optional<Farm> findById(UUID id);

    void delete(Farm farm);
}
