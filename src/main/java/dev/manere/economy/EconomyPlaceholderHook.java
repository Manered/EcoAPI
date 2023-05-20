package dev.manere.economy;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EconomyPlaceholderHook extends PlaceholderExpansion {

    private final EconomyAPIPlugin plugin;

    /**
     * Constructs a new EconomyPlaceholderHook instance.
     * @param plugin the main plugin instance
     */
    public EconomyPlaceholderHook(EconomyAPIPlugin plugin) {
        this.plugin = plugin;
    }

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
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "economy";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
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
            plugin.getPersistenceManager().loadPlayerData(player.getUniqueId());
            double balance = plugin.getPersistenceManager().getPlayerData(player.getUniqueId()).getBalance();
            return String.format("%.2f", balance);
        }

        return null;
    }

}
