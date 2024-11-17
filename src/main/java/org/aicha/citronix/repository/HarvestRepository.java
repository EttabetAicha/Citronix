package org.aicha.citronix.repository;

import org.aicha.citronix.domain.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Integer> {

    @Query("SELECT h FROM Harvest h WHERE h.season = :season")
    List<Harvest> findBySeason(String season);
}