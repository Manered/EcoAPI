package dev.manere.ecoapi.api.file;

import dev.manere.ecoapi.impl.EconomyPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Interface for caching and managing economy data for players.
 */
public interface EconomyCaching {
    /**
     * Loads data for all players.
     */
    void loadAll();

    /**
     * Saves data for all players.
     */
    void saveAll();

    /**
     * Retrieves the balance for a player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The balance of the player.
     */
    double balance(@NotNull UUID uuid);

    /**
     * Caches the data for the specified player.
     *
     * @param player The player whose data needs to be cached.
     */
    void cache(@NotNull EconomyPlayer player);

    /**
     * Loads the cached data for the player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The cached player data, or null if not found.
     */
    @Nullable EconomyPlayer load(@NotNull UUID uuid);

    /**
     * Loads player data from a file for the player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The loaded player data, or null if not found.
     */
    @Nullable EconomyPlayer loadFromFile(@NotNull UUID uuid);

    /**
     * Removes the cached data for the player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     */
    void remove(@NotNull UUID uuid);
}
