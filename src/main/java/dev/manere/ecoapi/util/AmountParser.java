package dev.manere.ecoapi.util;

public class AmountParser {

    public static double parseAmount(String amountString) throws NumberFormatException {
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
            throw new NumberFormatException(ColorUtils.translate("#ff0000Invalid amount format: " + amountString));
        }
    }

    public static String formatAmount(double amount) {
        if (amount >= 1000000000) {
            return String.format("%.1fB", amount / 1000000000);
        } else if (amount >= 1000000) {
            return String.format("%.1fM", amount / 1000000);
        } else if (amount >= 1000) {
            return String.format("%.1fK", amount / 1000);
        } else if (amount % 1 == 0) { // Check if the amount is an integer
            return String.format("%.0f", amount); // Use "%.0f" for integers
        } else {
            return String.format("%.2f", amount);
        }
    }
}
