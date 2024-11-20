package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
    List<Harvest> findByFarmId(UUID farmId);

    Optional<Harvest> findByFarmIdAndSeason(UUID farmId, Season season);
    List<Harvest> findBySeason(Season season);
}