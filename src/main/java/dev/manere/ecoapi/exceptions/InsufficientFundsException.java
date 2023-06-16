package dev.manere.ecoapi.exceptions;

import dev.manere.ecoapi.util.ColorUtils;

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
        super(
                ColorUtils.translate(message)
        );
    }
}