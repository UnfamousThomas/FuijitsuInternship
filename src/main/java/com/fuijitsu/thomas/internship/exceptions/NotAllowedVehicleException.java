package com.fuijitsu.thomas.internship.exceptions;

import com.fuijitsu.thomas.internship.model.util.VehicleType;

/**
 * Exception that is thrown whenever the specific vehicle is currently forbidden
 */
public class NotAllowedVehicleException extends RuntimeException {
    public NotAllowedVehicleException(VehicleType vehicleType) {
        super("Usage of selected vehicle (" + vehicleType + ") is forbidden");
    }
}
