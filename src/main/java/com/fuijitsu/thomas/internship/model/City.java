package com.fuijitsu.thomas.internship.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * POJO object for storing supported cities, also mapped to DB
 */
@Entity
@Table(name = "cities")
public class City {

    @Id
    private String cityname;
    private String matchingstation;
    private float bikeprice;
    private float carprice;
    private float scooterprice;

    public City(String cityName, String matchingstation, float bikeprice, float carprice, float scooterprice) {
        this.cityname = cityName;
        this.matchingstation = matchingstation;
        this.bikeprice = bikeprice;
        this.carprice = carprice;
        this.scooterprice = scooterprice;
    }

    public City() {}

    public String getCityname() {
        return cityname;
    }

    public float getBikeprice() {
        return bikeprice;
    }

    public float getCarprice() {
        return carprice;
    }

    public float getScooterprice() {
        return scooterprice;
    }

    public String getMatchingstation() {
        return matchingstation;
    }
}
