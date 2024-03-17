package com.fuijitsu.thomas.internship.exceptions;

/**
 * Exception that is thrown when some resource was not found. This is basically equivalent to 404.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String object, String name) {
        super("Could not find " + object + " with name "+ name);
    }
}
