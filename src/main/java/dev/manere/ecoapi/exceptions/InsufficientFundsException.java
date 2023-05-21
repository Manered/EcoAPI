package dev.manere.ecoapi.exceptions;

/**
 * Exception thrown when there are insufficient funds for an operation.
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * Constructs an instance of InsufficientFundsException.
     *
     * @param message the exception message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
