package dev.manere.ecoapi.init;

import dev.manere.ecoapi.api.EconomyHandler;
import dev.manere.ecoapi.api.file.EconomyCaching;
import dev.manere.ecoapi.api.file.EconomyFileHandler;
import dev.manere.ecoapi.impl.file.EconomyCachingImpl;
import dev.manere.ecoapi.impl.file.EconomyFileHandlerImpl;
import dev.manere.ecoapi.impl.EconomyHandlerImpl;
import dev.manere.ecoapi.impl.EconomyPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Static class providing access to various components of the economy system.
 */
public class Economy {
    /**
     * Retrieves the economy data for the specified online player.
     *
     * @param player The online player.
     * @return The economy data for the player, or null if not found.
     */
    @Nullable
    public static EconomyPlayer player(@NotNull Player player) {
        return caching().load(player.getUniqueId());
    }

    /**
     * Retrieves the economy data for the specified offline player.
     *
     * @param player The offline player.
     * @return The economy data for the player, or null if not found.
     */
    @Nullable
    public static EconomyPlayer offlinePlayer(@NotNull OfflinePlayer player) {
        return caching().load(player.getUniqueId());
    }

    /**
     * Returns an instance of the EconomyInitializer for initializing the economy system.
     *
     * @return An instance of EconomyInitializer.
     */
    public static EconomyInitializer initializer() {
        return new EconomyInitializer();
    }

    /**
     * Returns an instance of the EconomyFileHandler for handling economy data stored in files.
     *
     * @return An instance of EconomyFileHandler.
     */
    public static EconomyFileHandler fileHandler() {
        return new EconomyFileHandlerImpl();
    }

    /**
     * Returns an instance of the EconomyHandler for managing player balances.
     *
     * @return An instance of EconomyHandler.
     */
    public static EconomyHandler handler() {
        return new EconomyHandlerImpl();
    }

    /**
     * Returns an instance of the EconomyCaching for caching player data.
     *
     * @return An instance of EconomyCaching.
     */
    public static EconomyCaching caching() {
        return new EconomyCachingImpl();
    }

    // Private method to avoid instantiation of utility class
    private void a() {
        // this method does nothing. It is to avoid warning: Instantiation of utility class
    }
}
