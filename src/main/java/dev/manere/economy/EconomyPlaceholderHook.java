package dev.manere.economy;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EconomyPlaceholderHook extends PlaceholderExpansion {

    private final EconomyAPIPlugin plugin;

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
        return "Your Name";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "economy";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("balance")) {
            double balance = plugin.getPersistenceManager().getPlayerData(player.getUniqueId()).getBalance();
            return String.format("%.2f", balance);
        }

        return null;
    }

}