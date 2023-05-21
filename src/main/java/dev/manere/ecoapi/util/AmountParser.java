package dev.manere.ecoapi.util;

import dev.manere.ecoapi.exceptions.InvalidAmountException;

/**
 * Utility class for parsing amounts with shortcuts.
 */
public class AmountParser {

    private AmountParser() {
        // Private constructor to prevent instantiation
    }

    /**
     * Parses the amount string into a double value.
     *
     * @param amountString the amount string to parse
     * @return the parsed amount as a double
     * @throws InvalidAmountException if the amount string is not a valid number
     */
    public static double parseAmount(String amountString) throws InvalidAmountException {
        if (amountString.matches("[\\d.]+[kKmMbB]?")) {
            char suffix = amountString.charAt(amountString.length() - 1);
            double amount = Double.parseDouble(amountString.substring(0, amountString.length() - 1));
            switch (suffix) {
                case 'k':
                case 'K':
                    amount *= 1000;
                    break;
                case 'm':
                case 'M':
                    amount *= 1000000;
                    break;
                case 'b':
                case 'B':
                    amount *= 1000000000;
                    break;
            }
            return amount;
        } else {
            throw new InvalidAmountException(ColorUtils.translate("#ff0000Invalid amount format: " + amountString));
        }
    }
}
