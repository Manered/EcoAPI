package dev.manere.ecoapi.exceptions;

/**
 * Exception thrown when there is an error with economy data.
 */
public class EconomyDataException extends RuntimeException {

    /**
     * Constructs an instance of EconomyDataException.
     *
     * @param message the exception message
     */
    public EconomyDataException(String message) {
        super(message);
    }
}