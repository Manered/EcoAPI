package dev.manere.ecoapi.data;

import dev.manere.ecoapi.exceptions.EconomyDataException;
import dev.manere.ecoapi.model.PlayerData;
import dev.manere.ecoapi.util.ColorUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the economy data for players.
 */
public class EconomyManager {
    private final Map<String, PlayerData> playerDataMap;
    private final String dataFolderPath;

    /**
     * Constructs an instance of EconomyManager.
     */
    public EconomyManager() throws EconomyDataException {
        this.playerDataMap = new HashMap<>();
        this.dataFolderPath = "data/";

        // Create data folder if it doesn't exist
        try {
            Files.createDirectories(Path.of(dataFolderPath));
        } catch (IOException e) {
            throw new EconomyDataException(ColorUtils.translate("#ff0000Failed to create data folder: " + dataFolderPath));
        }
    }

    /**
     * Retrieves the player data for the given UUID.
     *
     * @param uuid the UUID of the player
     * @return the PlayerData object for the player
     */
    public synchronized PlayerData getPlayerData(String uuid) {
        return playerDataMap.computeIfAbsent(uuid, k -> {
            try {
                return loadPlayerData(uuid);
            } catch (EconomyDataException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private PlayerData loadPlayerData(String uuid) throws EconomyDataException {
        String fileName = dataFolderPath + uuid + ".dat";
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (PlayerData) inputStream.readObject();
        } catch (FileNotFoundException e) {
            // Player data file doesn't exist, create a new one
            PlayerData playerData = new PlayerData(uuid);
            savePlayerData(playerData);
            return playerData;
        } catch (IOException | ClassNotFoundException e) {
            throw new EconomyDataException(ColorUtils.translate("#ff0000Failed to load player data: " + uuid));
        }
    }

    /**
     * Saves the player data.
     *
     * @param playerData the PlayerData object to be saved
     */
    public synchronized void savePlayerData(PlayerData playerData) throws EconomyDataException {
        String fileName = dataFolderPath + playerData.getUuid() + ".dat";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(playerData);
        } catch (IOException e) {
            throw new EconomyDataException(ColorUtils.translate("#ff0000Failed to save player data: " + playerData.getUuid()));
        }
    }
}
