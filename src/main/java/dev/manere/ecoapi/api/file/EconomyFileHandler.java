package dev.manere.ecoapi.api.file;

import dev.manere.ecoapi.impl.EconomyPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Interface for handling economy data stored in files.
 */
public interface EconomyFileHandler {
    /**
     * Retrieves the player data from a file for the player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The player data, or null if not found.
     */
    @Nullable EconomyPlayer retrieve(@NotNull UUID uuid);

    /**
     * Retrieves or creates the player data with the specified UUID and initial balance.
     *
     * @param uuid    The UUID of the player.
     * @param balance The initial balance of the player.
     * @return The player data.
     */
    @NotNull EconomyPlayer retrieveOrCreate(@NotNull UUID uuid, double balance);

    /**
     * Saves the player data to a file for the player with the specified UUID.
     *
     * @param uuid    The UUID of the player.
     * @param balance The balance to save.
     * @return The saved player data.
     */
    @NotNull EconomyPlayer save(@NotNull UUID uuid, double balance);

    /**
     * Deletes the player data file for the player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     */
    void delete(@NotNull UUID uuid);
}
