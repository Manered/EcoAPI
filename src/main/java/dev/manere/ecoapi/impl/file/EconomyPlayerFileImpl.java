package dev.manere.ecoapi.impl.file;

import dev.manere.ecoapi.api.file.EconomyPlayerFile;
import dev.manere.ecoapi.impl.EconomyPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

/**
 * Implementation of the {@link EconomyPlayerFile} interface for managing player data files.
 */
public class EconomyPlayerFileImpl implements EconomyPlayerFile {
    private final EconomyPlayer player;

    /**
     * Constructs an EconomyPlayerFileImpl with the specified player.
     *
     * @param player The associated player.
     */
    public EconomyPlayerFileImpl(EconomyPlayer player) {
        this.player = player;
    }

    /**
     * Gets the file associated with the player data.
     *
     * @return The file associated with the player data.
     */
    @Override
    public @NotNull File file() {
        return new File(path());
    }

    /**
     * Loads the YAML configuration from the player data file.
     *
     * @return The YAML configuration from the player data file.
     */
    @Override
    public @NotNull YamlConfiguration yaml() {
        return YamlConfiguration.loadConfiguration(file());
    }

    /**
     * Gets the FileConfiguration associated with the player data.
     *
     * @return The FileConfiguration associated with the player data.
     */
    @Override
    public @NotNull FileConfiguration config() {
        return yaml();
    }

    /**
     * Gets the value associated with the specified key in the player data file.
     *
     * @param key The key for which to retrieve the value.
     * @return The value associated with the specified key.
     */
    @Override
    public Object val(@NotNull String key) {
        return yaml().get(key);
    }

    /**
     * Sets the value for the specified key in the player data file.
     *
     * @param key The key for which to set the value.
     * @param val The value to set.
     */
    @Override
    public void set(String key, Object val) {
        yaml().set(key, val);
    }

    /**
     * Gets the associated player.
     *
     * @return The associated player.
     */
    @Override
    public @NotNull EconomyPlayer player() {
        return player;
    }

    /**
     * Gets the UUID of the associated player.
     *
     * @return The UUID of the associated player.
     */
    @Override
    public @NotNull UUID uuid() {
        return player().uuid();
    }

    /**
     * Gets the path to the player data file.
     *
     * @return The path to the player data file.
     */
    @Override
    public @NotNull String path() {
        return "economy/dev/manere/ecoapi/data/" + uuid() + ".yml";
    }

    /**
     * Returns a string representation of the EconomyPlayerFileImpl.
     *
     * @return A string representation of the EconomyPlayerFileImpl.
     */
    @Override
    public String toString() {
        return "EconomyPlayerFileImpl[player=" + uuid() + "]";
    }
}
