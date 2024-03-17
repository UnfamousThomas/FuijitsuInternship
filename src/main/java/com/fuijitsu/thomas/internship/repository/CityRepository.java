package com.fuijitsu.thomas.internship.repository;

import com.fuijitsu.thomas.internship.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Simple repository for interacting with the DB for Cities.
 */
@Repository
public interface CityRepository extends JpaRepository<City, String> {

    @Query("SELECT c.cityname FROM City c")
    Set<String> getCityNames();

    @Query("SELECT c.matchingstation FROM City c")
    Set<String> getStationsToMonitor();
}
