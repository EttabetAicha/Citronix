package org.aicha.citronix.service;


import org.aicha.citronix.domain.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FarmService {

    Farm save(Farm farm);

    Optional<Farm> findById(UUID id);

    Page<Farm> findAll(Pageable pageable);

    void delete(Farm farm);

    List<Farm> searchFarms(String name, String location, LocalDate startDate);
}
