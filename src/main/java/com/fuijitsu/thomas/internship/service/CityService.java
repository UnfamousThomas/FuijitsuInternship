package com.fuijitsu.thomas.internship.service;

import com.fuijitsu.thomas.internship.exceptions.NotFoundException;
import com.fuijitsu.thomas.internship.model.City;
import com.fuijitsu.thomas.internship.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City updateCity(City city) {
        return cityRepository.save(city);
    }
    public void removeCity(String id) {
        City city = cityRepository.findById(id).orElse(null);
        if(city == null) {
            throw new NotFoundException("city", id);
        }

        cityRepository.delete(city);
    }
}
