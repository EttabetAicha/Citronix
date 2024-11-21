package org.aicha.citronix.repository;

import org.aicha.citronix.domain.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {
    List<HarvestDetail> findByHarvestId(UUID harvestId);
    List<HarvestDetail> findByTreeId(UUID treeId);
}