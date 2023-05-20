package dev.manere.ecoapi.data;

import dev.manere.ecoapi.exceptions.EconomyDataException;
import dev.manere.ecoapi.model.PlayerData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EconomyManager {
    private static final String DATA_FOLDER = "data/players/";

    public PlayerData getPlayerData(String uuid) throws EconomyDataException {
        Path playerDataFile = getPlayerDataFile(uuid);
        if (Files.exists(playerDataFile)) {
            try {
                String data = Files.readString(playerDataFile);
                String[] parts = data.split(":");
                if (parts.length == 2) {
                    String playerUuid = parts[0];
                    double balance = Double.parseDouble(parts[1]);
                    return new PlayerData(playerUuid, balance);
                }
            } catch (IOException | NumberFormatException e) {
                throw new EconomyDataException("Failed to retrieve player data for UUID: " + uuid, e);
            }
        }

        // Player data not found, return a new PlayerData object with default values
        return new PlayerData(uuid, 0);
    }

    public void savePlayerData(PlayerData playerData) throws EconomyDataException {
        String data = playerData.getUuid() + ":" + playerData.getBalance();
        Path playerDataFile = getPlayerDataFile(playerData.getUuid());
        try {
            Files.writeString(playerDataFile, data);
        } catch (IOException e) {
            throw new EconomyDataException("Failed to save player data for UUID: " + playerData.getUuid(), e);
        }
    }

    public void deletePlayerData(String uuid) throws EconomyDataException {
        Path playerDataFile = getPlayerDataFile(uuid);
        try {
            Files.deleteIfExists(playerDataFile);
        } catch (IOException e) {
            throw new EconomyDataException("Failed to delete player data for UUID: " + uuid, e);
        }
    }

    private Path getPlayerDataFile(String uuid) {
        File dataFolder = new File(DATA_FOLDER);
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        return Path.of(DATA_FOLDER, uuid + ".dat");
    }
}
