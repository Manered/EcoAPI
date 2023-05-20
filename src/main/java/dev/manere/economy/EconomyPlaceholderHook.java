package dev.manere.economy;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EconomyPlaceholderHook extends PlaceholderExpansion {

    private final EconomyAPIPlugin economyAPIPlugin = (EconomyAPIPlugin) Bukkit.getPluginManager().getPlugin("EconomyPlugin");

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return economyAPIPlugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "economy";
    }

    @Override
    public @NotNull String getVersion() {
        return economyAPIPlugin.getDescription().getVersion();
    }

    /**
     * Retrieves the value of the requested placeholder.
     * @param player the player to retrieve the placeholder value for
     * @param identifier the identifier of the placeholder
     * @return the value of the placeholder, or null if not found
     */
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("balance")) {
            economyAPIPlugin.getPersistenceManager().loadPlayerData(player.getUniqueId());
            double balance = economyAPIPlugin.getPersistenceManager().getPlayerData(player.getUniqueId()).getBalance();
            return String.format("%.2f", balance);
        }

        return null;
    }

}
