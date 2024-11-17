package org.aicha.citronix.repository;

import org.aicha.citronix.domain.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Integer> {

    @Query("SELECT hd FROM HarvestDetail hd WHERE hd.tree.id = :treeId")
    List<HarvestDetail> findByTreeId(Integer treeId);
}