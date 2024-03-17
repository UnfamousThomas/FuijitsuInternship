package com.fuijitsu.thomas.internship.service;

import com.fuijitsu.thomas.internship.exceptions.NotAllowedVehicleException;
import com.fuijitsu.thomas.internship.model.City;
import com.fuijitsu.thomas.internship.model.util.VehicleType;
import com.fuijitsu.thomas.internship.repository.CityRepository;
import com.fuijitsu.thomas.internship.repository.ObservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = { DeliveryService.class },webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DeliveryMockTest {

    @MockBean
    private ObservationRepository observationRepository;
    @MockBean
    private CityRepository cityRepository;
    @Autowired
    private DeliveryService deliveryService;
    float initialFee = 5;


    @Test
    public void testWithWindSpeed() {
        Assertions.assertEquals(initialFee, deliveryService.calculateWithWindspeed(initialFee, 1, VehicleType.CAR));
        Assertions.assertEquals(initialFee+0.5F, deliveryService.calculateWithWindspeed(initialFee, 11, VehicleType.BIKE));
        Assertions.assertThrows(NotAllowedVehicleException.class, () -> deliveryService.calculateWithWindspeed(initialFee, 50, VehicleType.BIKE));
    }

    @Test
    public void testWithAirTemp() {
        Assertions.assertEquals(initialFee, deliveryService.calculateWithAirTemp(initialFee, -20, VehicleType.CAR));
        Assertions.assertEquals(initialFee+1F, deliveryService.calculateWithAirTemp(initialFee, -12, VehicleType.BIKE));
        Assertions.assertEquals(initialFee+0.5F, deliveryService.calculateWithAirTemp(initialFee, -9, VehicleType.BIKE));
    }

    @Test
    public void testWithPhenomen() {
        Assertions.assertEquals(initialFee+1, deliveryService.calculateWithPhenomen(initialFee, "Drifting snow", VehicleType.BIKE));
        Assertions.assertEquals(initialFee+1, deliveryService.calculateWithPhenomen(initialFee, "Moderate sleet", VehicleType.SCOOTER));
        Assertions.assertEquals(initialFee+0.5F, deliveryService.calculateWithPhenomen(initialFee, "Light shower", VehicleType.SCOOTER));
        Assertions.assertEquals(initialFee+0.5F, deliveryService.calculateWithPhenomen(initialFee, "Heavy rain", VehicleType.BIKE));
        Assertions.assertThrows(NotAllowedVehicleException.class, () -> deliveryService.calculateWithPhenomen(initialFee, "HAIL", VehicleType.SCOOTER));
    }

    @Test
    public void testRegional() {
        City city = new City("region", "match", 3.0F, 1.0F, 2.0F);
        Assertions.assertEquals(3.0, deliveryService.getRegionalBaseFee(city, VehicleType.BIKE));
        Assertions.assertEquals(1.0, deliveryService.getRegionalBaseFee(city, VehicleType.CAR));
        Assertions.assertEquals(2.0, deliveryService.getRegionalBaseFee(city, VehicleType.SCOOTER));
    }
}
