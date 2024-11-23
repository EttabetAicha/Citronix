package org.aicha.citronix.repository;


import org.aicha.citronix.domain.HarvestDetail;
import org.aicha.citronix.domain.Tree;
import org.aicha.citronix.domain.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Integer> {
    List<HarvestDetail> findByHarvestId(UUID harvestId);
    List<HarvestDetail> findByTreeId(UUID treeId);
    @Query("SELECT hd FROM HarvestDetail hd WHERE hd.harvest.season = :season AND hd.tree = :tree")
    List<HarvestDetail> findByTreeAndSeason(@Param("tree") Tree tree, @Param("season") Season season);
}
