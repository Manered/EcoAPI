package dev.manere.ecoapi.impl.file;

import dev.manere.ecoapi.init.Economy;
import dev.manere.ecoapi.api.file.EconomyFileHandler;
import dev.manere.ecoapi.init.EconomyInitializer;
import dev.manere.ecoapi.impl.EconomyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Implementation of the {@link EconomyFileHandler} interface for handling economy data stored in files.
 */
public class EconomyFileHandlerImpl implements EconomyFileHandler {
    /**
     * Retrieves the player data from a file for the player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The player data, or null if not found.
     */
    @Override
    public EconomyPlayer retrieve(@NotNull UUID uuid) {
        File file = new File("economy/dev/manere/ecoapi/data/" + uuid + ".yml");

        if (file.exists()) {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            return EconomyPlayer.of(uuid)
                    .balance(configuration.getDouble("balance"));
        } else {
            return null;
        }
    }

    /**
     * Retrieves or creates the player data with the specified UUID and initial balance.
     *
     * @param uuid    The UUID of the player.
     * @param balance The initial balance of the player.
     * @return The player data.
     */
    @Override
    public @NotNull EconomyPlayer retrieveOrCreate(@NotNull UUID uuid, double balance) {
        return save(uuid, balance);
    }

    /**
     * Saves the player data to a file for the player with the specified UUID.
     *
     * @param uuid    The UUID of the player.
     * @param balance The balance to save.
     * @return The saved player data.
     */
    @Override
    public @NotNull EconomyPlayer save(@NotNull UUID uuid, double balance) {
        File file = new File("economy/dev/manere/ecoapi/data/" + uuid + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("balance", balance);

        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return EconomyPlayer.of(uuid, balance);
    }

    /**
     * Deletes the player data file for the player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     */
    @Override
    public void delete(@NotNull UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(EconomyInitializer.plugin(), () -> {
            File file = new File("economy/dev/manere/ecoapi/data/" + uuid + ".yml");

            if (file.exists()) {
                file.delete();
            }

            Economy.caching().remove(uuid);
        });
    }
}
