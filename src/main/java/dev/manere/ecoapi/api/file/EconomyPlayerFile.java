package dev.manere.ecoapi.api.file;

import dev.manere.ecoapi.impl.EconomyPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;

/**
 * Interface for handling individual player data files.
 */
public interface EconomyPlayerFile {
    /**
     * Gets the file associated with this player data.
     *
     * @return The player data file.
     */
    @NotNull File file();

    /**
     * Gets the YAML configuration associated with this player data.
     *
     * @return The YAML configuration.
     */
    @NotNull YamlConfiguration yaml();

    /**
     * Gets the Bukkit FileConfiguration associated with this player data.
     *
     * @return The Bukkit FileConfiguration.
     */
    @NotNull FileConfiguration config();

    /**
     * Gets the value associated with the specified key from the player data.
     *
     * @param key The key to retrieve.
     * @return The value associated with the key, or null if not found.
     */
    @Nullable Object val(@NotNull String key);

    /**
     * Sets the value associated with the specified key in the player data.
     *
     * @param key The key to set.
     * @param val The value to associate with the key.
     */
    void set(String key, Object val);

    /**
     * Gets the EconomyPlayer object associated with this player data.
     *
     * @return The EconomyPlayer object.
     */
    @NotNull EconomyPlayer player();

    /**
     * Gets the UUID of the player associated with this player data.
     *
     * @return The UUID of the player.
     */
    @NotNull UUID uuid();

    /**
     * Gets the path of the player data file.
     *
     * @return The path of the player data file.
     */
    @NotNull String path();
}
