package com.fuijitsu.thomas.internship.exceptions.handlers;

import com.fuijitsu.thomas.internship.exceptions.NotAllowedVehicleException;
import com.fuijitsu.thomas.internship.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandler {

    /**
     * Handles what to do when NotFoundException is thrown during a request
     * @param exception NotFoundException that was thrown
     * @return A map with the message, so it is parsed as json
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, String> handleNotFoundException(NotFoundException exception) {
        return createErrorMap(exception.getMessage());
    }
    /**
     * Handles what to do when NotAllowedVehicleException is thrown during a request
     * @param exception NotAllowedVehicleException that was thrown
     * @return A map with the message, so it is parsed as json
     */
    @ExceptionHandler(NotAllowedVehicleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Map<String, String> handleForbiddenVehicleException(NotAllowedVehicleException exception) {
        return createErrorMap(exception.getMessage());
    }

    /**
     * Utility method that makes the error into a map
     * @param message Message of the exception
     * @return Map which is used so we get JSON
     */
    private Map<String, String> createErrorMap(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("message", message);

        return error;
    }
}
