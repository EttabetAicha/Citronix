package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Query("SELECT s FROM Sale s WHERE s.clientName LIKE %:clientName%")
    List<Sale> findByClientName(String clientName);

    List<Sale> findByHarvestId(UUID harvestId);
}