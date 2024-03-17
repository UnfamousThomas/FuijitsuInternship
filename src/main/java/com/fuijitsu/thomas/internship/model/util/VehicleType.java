package com.fuijitsu.thomas.internship.model.util;

import com.fuijitsu.thomas.internship.exceptions.NotFoundException;

/**
 * Simple enum for having a list of possible vehicle types
 */
public enum VehicleType {
    CAR,
    SCOOTER,
    BIKE;

    /**
     * Gets the vehicle type from a string
     * @param vehicleType Vehicle type as string
     * @return Proper enum value or NotFoundException.
     */
    public static VehicleType getByString(String vehicleType) {
        vehicleType = vehicleType.replaceAll("_", "");
        for (VehicleType value : values()) {
            if(value.name().equalsIgnoreCase(vehicleType)) return value;
        }
        throw new NotFoundException("vehicle", vehicleType);
    }
}
