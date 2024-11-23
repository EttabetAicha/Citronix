package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.domain.Harvest;
import org.aicha.citronix.domain.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
    List<Harvest> findByFarmId(UUID farmId);

    Optional<Harvest> findByFarmIdAndSeason(UUID farmId, Season season);
    List<Harvest> findBySeason(Season season);

    List<Harvest> findByFarmAndSeason(Farm farm, Season season);
}
