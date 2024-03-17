package com.fuijitsu.thomas.internship.controller;

import com.fuijitsu.thomas.internship.model.City;
import com.fuijitsu.thomas.internship.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the /api/cities path
 */
@RestController
@RequestMapping(path = "/api/cities")
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Used for registering new cities
     * @param city City object sent via JSON
     * @return City object that was saved
     */
    @PostMapping()
    public City addCity(@RequestBody City city) {
        return cityService.updateCity(city);
    }

    /**
     * Used for editing cities
     * @param city City object.
     * @return City object that was updated
     */
    @PutMapping
    public City updateCity(@RequestBody City city) {
        return cityService.updateCity(city);
    }

    /**
     * Used for deleting cities
     * @param city City object. Important part is cityname
     */
    @DeleteMapping
    public void removeCity(@RequestBody City city) {
        cityService.removeCity(city.getCityname());
    }

}
