package com.fuijitsu.thomas.internship.controller;

import com.fuijitsu.thomas.internship.model.util.VehicleType;
import com.fuijitsu.thomas.internship.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the /api/deliveries path
 */
@RestController
@RequestMapping(path = "/api/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    /**
     * Main functionality, for which we use request params to feed variables
     * @param city City name that we wish to find the parameter for
     * @param vehicle Vehicle we are checking fee for
     * @param timestamp Non-required parameter, if given, the nearest value fee is given
     * @return Map so we can return it in json format, with "fee": price
     */
    @GetMapping(path="/fee")
    public Map<String, Float> getDeliveryFee(@RequestParam String city, @RequestParam String vehicle, @RequestParam(required = false) LocalDateTime timestamp) {
        VehicleType vehicleType = VehicleType.getByString(vehicle);
        Map<String, Float> feeMap = new HashMap<>();
        feeMap.put("fee", deliveryService.getFeeForCity(city.toLowerCase(), vehicleType, timestamp));
        return feeMap;
    }
}
