package dev.manere.ecoapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for color-related operations.
 */
public class ColorUtils {

    private ColorUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Translates the color codes in a string to the appropriate format for display.
     *
     * @param message the message string containing color codes
     * @return the translated message string
     */
    public static String translate(String message) {
        final Pattern colorPattern = Pattern.compile("(#[A-Fa-f0-9]{6})|(&[0-9a-fk-or])");
        Matcher matcher = colorPattern.matcher(message);
        StringBuilder buffer = new StringBuilder(message.length() + 4 * 8);

        // Track the current active color
        String activeColor = null;

        while (matcher.find()) {
            String group = matcher.group();

            if (group.startsWith("#")) {
                String hexCode = group.substring(1);

                // Convert the hex color code to Minecraft formatting codes
                StringBuilder colorCode = new StringBuilder("§x");
                for (int i = 0; i < 6; i += 2) {
                    colorCode.append("§").append(hexCode.charAt(i)).append("§").append(hexCode.charAt(i + 1));
                }

                if (!colorCode.toString().equals(activeColor)) {
                    matcher.appendReplacement(buffer, colorCode.toString());
                    activeColor = colorCode.toString();
                }
            } else if (group.startsWith("&")) {
                // Use Minecraft color code as is
                String colorCode = group.substring(0, 2).replace('&', '§');
                if (!colorCode.equals(activeColor)) {
                    matcher.appendReplacement(buffer, colorCode);
                    activeColor = colorCode;
                }
            }
        }

        return matcher.appendTail(buffer).toString();
    }
}