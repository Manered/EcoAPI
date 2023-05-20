package dev.manere.ecoapi.exceptions;

public class EconomyDataException extends Exception {
    public EconomyDataException(String message) {
        super(message);
    }

    public EconomyDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
