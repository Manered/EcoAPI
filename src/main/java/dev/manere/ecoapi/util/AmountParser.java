package dev.manere.ecoapi.util;

public class AmountParser {

    public static double parseAmount(String amountString) throws NumberFormatException {
        if (amountString.matches("[\\d.]+[kKmMbB]?")) {
            char suffix = amountString.charAt(amountString.length() - 1);
            double amount = Double.parseDouble(amountString.substring(0, amountString.length() - 1));
            switch (suffix) {
                case 'k', 'K' -> amount *= 1000;
                case 'm', 'M' -> amount *= 1000000;
                case 'b', 'B' -> amount *= 1000000000;
            }
            return amount;
        } else {
            throw new NumberFormatException("Invalid amount format: " + amountString);
        }
    }

    public static String formatAmount(double amount) {
        if (amount >= 1000000000) {
            return String.format("%.1fB", amount / 1000000000);
        } else if (amount >= 1000000) {
            return String.format("%.1fM", amount / 1000000);
        } else if (amount >= 1000) {
            return String.format("%.1fK", amount / 1000);
        } else {
            return String.format("%.2f", amount);
        }
    }

}
