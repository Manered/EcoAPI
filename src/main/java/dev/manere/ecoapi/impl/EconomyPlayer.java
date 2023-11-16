package dev.manere.ecoapi.impl;

import dev.manere.ecoapi.init.Economy;
import dev.manere.ecoapi.api.file.EconomyPlayerFile;
import dev.manere.ecoapi.impl.file.EconomyPlayerFileImpl;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This data class represents a player's economic profile, including their UUID and balance.
 * It provides methods for creating instances of players with specific attributes, managing their balance,
 * accessing their UUID, and interacting with the caching and storage mechanisms of the associated economy system.
 */
public class EconomyPlayer {
    private UUID uuid;
    private double balance;

    /**
     * Constructs an EconomyPlayer with the specified UUID and initializes the balance using the
     * caching mechanism of the associated economy system.
     *
     * @param uuid The UUID of the player.
     */
    public EconomyPlayer(UUID uuid) {
        this.uuid = uuid;
        this.balance = Economy.caching().balance(uuid);
    }

    /**
     * Creates and returns an EconomyPlayer instance for the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return An EconomyPlayer instance.
     */
    public static EconomyPlayer of(UUID uuid) {
        return new EconomyPlayer(uuid);
    }

    /**
     * Creates and returns an EconomyPlayer instance for the specified UUID and sets the initial balance.
     *
     * @param uuid    The UUID of the player.
     * @param balance The initial balance of the player.
     * @return An EconomyPlayer instance with the specified balance.
     */
    public static EconomyPlayer of(UUID uuid, double balance) {
        return EconomyPlayer.of(uuid).balance(balance);
    }
    /**
     * Creates and returns an EconomyPlayer instance for the specified Player and sets the initial balance.
     *
     * @param player  The Bukkit Player object.
     * @param balance The initial balance of the player.
     * @return An EconomyPlayer instance with the specified balance.
     */
    public static EconomyPlayer of(Player player, double balance) {
        return EconomyPlayer.of(player.getUniqueId(), balance);
    }

    /**
     * Creates and returns an EconomyPlayer instance for the specified OfflinePlayer and sets the initial balance.
     *
     * @param offlinePlayer The Bukkit OfflinePlayer object.
     * @param balance       The initial balance of the player.
     * @return An EconomyPlayer instance with the specified balance.
     */
    public static EconomyPlayer of(OfflinePlayer offlinePlayer, double balance) {
        return EconomyPlayer.of(offlinePlayer.getUniqueId(), balance);
    }

    /**
     * Retrieves the UUID of the player associated with this EconomyPlayer.
     *
     * @return The UUID of the player.
     */
    public UUID uuid() {
        return uuid;
    }

    /**
     * Sets the UUID of the player associated with this EconomyPlayer.
     *
     * @param uuid The UUID of the player.
     * @return This EconomyPlayer instance.
     */
    public EconomyPlayer uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Retrieves the balance of the player associated with this EconomyPlayer.
     *
     * @return The balance of the player.
     */
    public double balance() {
        return balance;
    }

    /**
     * Sets the balance of the player associated with this EconomyPlayer.
     *
     * @param balance The balance to set.
     * @return This EconomyPlayer instance.
     */
    public EconomyPlayer balance(double balance) {
        this.balance = balance;
        return this;
    }

    /**
     * Retrieves an EconomyPlayerFile instance associated with this EconomyPlayer.
     *
     * @return An EconomyPlayerFile instance.
     */
    public EconomyPlayerFile economyPlayerFile() {
        return new EconomyPlayerFileImpl(this);
    }

    /**
     * Caches the current state of this EconomyPlayer using the caching mechanism of the associated economy system.
     */
    public void cache() {
        Economy.caching().cache(this);
    }

    /**
     * Alias for the {@link #cache()} method, used to update the cached state of this EconomyPlayer.
     */
    public void update() {
        cache();
    }

    /**
     * Alias for the {@link #cache()} method, used to save the state of this EconomyPlayer.
     */
    public void save() {
        cache();
    }
}
