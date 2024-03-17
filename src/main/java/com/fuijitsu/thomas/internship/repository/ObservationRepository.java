package com.fuijitsu.thomas.internship.repository;

import com.fuijitsu.thomas.internship.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Simple repository for interacting with the DB for Observations.
 */
@Repository
public interface ObservationRepository extends JpaRepository<Observation, Integer> {

    @Query("SELECT o from Observation o where o.cityname=?1 ORDER BY o.timestamp DESC limit 1")
    Observation findLatestObservationByCity(String cityname);

    @Query("SELECT o from Observation o WHERE o.cityname=?1 ORDER BY ABS(o.timestamp - ?2) LIMIT 1")
    Observation findClosestObservationByTimestamp(String name, long timestamp);
}
