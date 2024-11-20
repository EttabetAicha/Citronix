package org.aicha.citronix.repository;

import org.aicha.citronix.domain.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Integer> {

    @Query("SELECT hd FROM HarvestDetail hd WHERE hd.tree.id = :treeId")
    List<HarvestDetail> findByHarvestId(UUID harvestId);

    // Method to find HarvestDetails by Tree ID
    List<HarvestDetail> findByTreeId(UUID treeId);
}