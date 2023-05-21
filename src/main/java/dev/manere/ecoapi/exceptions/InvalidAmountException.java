package dev.manere.ecoapi.exceptions;

/**
 * Exception thrown when an invalid amount is encountered.
 */
public class InvalidAmountException extends RuntimeException {

    /**
     * Constructs an instance of InvalidAmountException.
     *
     * @param message the exception message
     */
    public InvalidAmountException(String message) {
        super(message);
    }
}