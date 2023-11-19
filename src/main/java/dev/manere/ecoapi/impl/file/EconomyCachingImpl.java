package dev.manere.ecoapi.impl.file;

import dev.manere.ecoapi.api.file.EconomyCaching;
import dev.manere.ecoapi.impl.EconomyPlayer;
import dev.manere.ecoapi.init.Economy;
import dev.manere.ecoapi.init.EconomyInitializer;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the {@link EconomyCaching} interface for caching player economy data.
 */
public class EconomyCachingImpl implements EconomyCaching {
    private static final Map<UUID, EconomyPlayer> cached = new ConcurrentHashMap<>();

    /**
     * Asynchronously loads data for all players from their respective files.
     */
    @Override
    public void loadAll() {
        Bukkit.getScheduler().runTaskAsynchronously(EconomyInitializer.plugin(), () -> {
            File dir = new File("economy/dev/manere/ecoapi/data/");
            if (!dir.exists()) return;

            File[] files = dir.listFiles((directory, name) -> name.endsWith(".yml"));
            if (files == null) return;

            for (File file : files) {
                cached.put(
                        UUID.fromString(file.getName().replace(".yml", "")),
                        Economy.fileHandler().retrieveOrCreate(UUID.fromString(file.getName().replace(".yml", "")), 0.0)
                );
            }
        });
    }

    /**
     * Saves data for all cached players.
     */
    @Override
    public void saveAll() {
        for (Map.Entry<UUID, EconomyPlayer> entry : cached.entrySet()) {
            entry.getValue().cache();
        }
    }

    /**
     * Retrieves the balance for a player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The balance of the player, or 0.0 if not found or balance is 0.0.
     */
    @Override
    public double balance(@NotNull UUID uuid) {
        EconomyPlayer player = load(uuid);
        return player == null ? 0.0 : player.balance() != 0.0 ? player.balance() : 0.0;
    }

    /**
     * Caches the data for the specified player, and saves it to file if not already present.
     *
     * @param player The player whose data needs to be cached.
     */
    @Override
    public void cache(@NotNull EconomyPlayer player) {
        cached.put(player.uuid(), player);

        // Save to file if not already present
        if (Economy.fileHandler().retrieve(player.uuid()) == null) {
            Economy.fileHandler().save(player.uuid(), player.balance());
        }

        // Update balance in player's file
        player.economyPlayerFile().set("balance", player.balance());
    }

    /**
     * Loads the cached data for the player with the specified UUID.
     * If not cached, attempts to load from file.
     *
     * @param uuid The UUID of the player.
     * @return The cached player data, or null if not found.
     */
    @Override
    public EconomyPlayer load(@NotNull UUID uuid) {
        if (cached.containsKey(uuid) && cached.get(uuid) != null) {
            return cached.get(uuid);
        }
        return loadFromFile(uuid);
    }

    /**
     * Loads player data from a file for the player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The loaded player data, or null if not found.
     */
    @Override
    public EconomyPlayer loadFromFile(@NotNull UUID uuid) {
        return Economy.fileHandler().retrieve(uuid);
    }

    /**
     * Removes the cached data for the player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     */
    @Override
    public void remove(@NotNull UUID uuid) {
        if (cached.containsKey(uuid) && cached.get(uuid) != null) {
            cached.remove(uuid);
        }
    }

    /**
     * Gets the map of cached player data.
     *
     * @return The map of cached player data.
     */
    public static Map<UUID, EconomyPlayer> cached() {
        return cached;
    }
}
