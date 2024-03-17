package com.fuijitsu.thomas.internship.model;

import jakarta.persistence.*;

/**
 * POJO for storing each weather observation. Also mapped to DB.
 */
@Entity
@Table(name = "observations")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cityname;
    private float airtemp;
    private long timestamp;
    private String phenomen;
    private float windspeed;

    public Observation(String cityname, float airtemp, long timestamp, String phenomen, float windspeed) {
        this.cityname = cityname;
        this.airtemp = airtemp;
        this.timestamp = timestamp;
        this.phenomen = phenomen;
        this.windspeed = windspeed;
    }

    public Observation() {}

    public float getAirtemp() {
        return airtemp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPhenomen() {
        return phenomen;
    }

    public float getWindspeed() {
        return windspeed;
    }


    @Override
    public String toString() {
        return String.format("%s, %f, %d, %s, %f",
                cityname, airtemp, timestamp, phenomen, windspeed);
    }
}
