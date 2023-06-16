package dev.manere.ecoapi.exceptions;

import dev.manere.ecoapi.util.ColorUtils;

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
        super(
                ColorUtils.translate(message)
        );
    }
}