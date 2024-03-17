package com.fuijitsu.thomas.internship.service;

import com.fuijitsu.thomas.internship.exceptions.NotAllowedVehicleException;
import com.fuijitsu.thomas.internship.exceptions.NotFoundException;
import com.fuijitsu.thomas.internship.model.util.VehicleType;
import com.fuijitsu.thomas.internship.model.City;
import com.fuijitsu.thomas.internship.model.Observation;
import com.fuijitsu.thomas.internship.repository.CityRepository;
import com.fuijitsu.thomas.internship.repository.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Service that handles most of the fee calculation logic
 */
@Service
public class DeliveryService {
    private final ObservationRepository observationRepository;
    private final CityRepository cityRepository;

    @Autowired
    public DeliveryService(ObservationRepository observationRepository, CityRepository cityRepository) {
        this.observationRepository = observationRepository;
        this.cityRepository = cityRepository;
    }


    /**
     * Calculates fee for a city
     * @param cityName Cityname, lowercase
     * @param vehicleType Vehicle Type enum, already resolved by this point
     * @param timestamp Can be null, in which case latest is used, otherwise the closest to this timestamp is used
     * @return The total delivery fee.
     */
    public float getFeeForCity(String cityName, VehicleType vehicleType, LocalDateTime timestamp) {
        City city = cityRepository.findById(cityName).orElse(null);
        if(city == null) throw new NotFoundException("City", cityName);
        float fee = getRegionalBaseFee(city, vehicleType);
        Observation observation;
        if(timestamp != null) {
            observation = observationRepository.findClosestObservationByTimestamp(city.getMatchingstation(), timestamp.toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
        } else {
            observation = observationRepository.findLatestObservationByCity(city.getMatchingstation());
        }
        if(observation == null) throw new NotFoundException("Observation", city.getMatchingstation());
        fee = calculateWithAirTemp(fee, observation.getAirtemp(), vehicleType);
        fee = calculateWithWindspeed(fee, observation.getWindspeed(), vehicleType);
        fee = calculateWithPhenomen(fee, observation.getPhenomen(), vehicleType);
        return fee;
    }

    /**
     * Calculates the fee after accounting for air temp
     * @param fee Fee before calculations
     * @param airTemp Air temp in the specific city
     * @param vehicleType Vehicle used
     * @return fee with airtemp accounted for
     */
    protected float calculateWithAirTemp(float fee, float airTemp, VehicleType vehicleType) {
        if(vehicleType == VehicleType.CAR) return fee;
        if(airTemp < -10) {
            fee+=1;
        } else if(airTemp<0 && airTemp>-10) fee+= 0.5F;

        return fee;
    }

    /**
     * Calculates the fee after accounting for windspeed
     * @param fee Fee before windspeed calculations
     * @param windSpeed Windspeed in specific city
     * @param vehicleType Vehicle used
     * @return fee after windspeed accounted for
     */
    protected float calculateWithWindspeed(float fee, float windSpeed, VehicleType vehicleType) {
        if(vehicleType != VehicleType.BIKE) return fee;

        if(windSpeed > 10 && windSpeed < 20) return fee + 0.5F;
        if(windSpeed > 20) throw new NotAllowedVehicleException(vehicleType);
        return fee;
    }

    /**
     * Calculates the base fee for each city
     * @param city City we are finding the base fee for
     * @param vehicleType Vehicle the deliverer is using
     * @return The base fee for specific city and vehicle
     */
    protected float getRegionalBaseFee(City city, VehicleType vehicleType) {
        switch (vehicleType) {
            case CAR -> {
                return city.getCarprice();
            }
            case BIKE -> {
                return city.getBikeprice();
            }
            case SCOOTER -> {
                return city.getScooterprice();
            }
        }
        throw new NotFoundException("vehicle", vehicleType.name());
    }

    /**
     * Method to calculate fee after phenomens.
     * @param fee Initial fee
     * @param phenomen Weather event
     * @param vehicleType Vehicle used
     * @return Returns the fee or throws an exception after accounting for phenomens. e.g rain, thunder, snow etc.
     */

    protected float calculateWithPhenomen(float fee, String phenomen, VehicleType vehicleType) {
        if(vehicleType == VehicleType.CAR) return fee;
        if(phenomen == null) return fee;
        phenomen=phenomen.toLowerCase();
        if(phenomen.contains("snow") || phenomen.contains("sleet")) return fee+1;
        if(phenomen.contains("rain") || phenomen.equals("light shower") || phenomen.equals("moderate shower") || phenomen.equals("heavy shower")) return fee+0.5F;
        if(phenomen.contains("thunder") || phenomen.contains("glaze") || phenomen.contains("hail")) throw new NotAllowedVehicleException(vehicleType);
        return fee;
    }
}
