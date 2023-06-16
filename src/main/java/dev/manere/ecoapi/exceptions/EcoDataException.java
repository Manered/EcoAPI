package dev.manere.ecoapi.exceptions;

import dev.manere.ecoapi.util.ColorUtils;

/**
 * Exception thrown when there is an error with economy data.
 */
public class EcoDataException extends RuntimeException {

    /**
     * Constructs an instance of EconomyDataException.
     *
     * @param message the exception message
     */
    public EcoDataException(String message) {
        super(
                ColorUtils.translate(message)
        );
    }
}