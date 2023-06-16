package dev.manere.ecoapi.managers;

import dev.manere.ecoapi.exceptions.EcoDataException;
import dev.manere.ecoapi.player.EcoPlayer;
import dev.manere.ecoapi.util.AmountParser;
import dev.manere.ecoapi.util.ColorUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the economy data for players.
 */
public class EcoManager {
    private final Map<String, EcoPlayer> playerDataMap;
    private final File dataFolder;

    /**
     * Constructs an instance of EcoManager.
     */
    public EcoManager() throws EcoDataException {
        this.playerDataMap = new HashMap<>();
        this.dataFolder = new File("eco/data");

        if (!dataFolder.exists()) {
            if (!dataFolder.mkdirs()) {
                throw new EcoDataException(ColorUtils.translate("Failed to create data folder: " + dataFolder.getPath()));
            }
        }
    }

    /**
     * Retrieves the player data for the given UUID.
     *
     * @param uuid the UUID of the player
     * @return the EcoPlayer object for the player
     */
    public synchronized EcoPlayer getPlayerData(String uuid) {
        return playerDataMap.computeIfAbsent(uuid, k -> {
            try {
                return loadPlayerData(uuid);
            } catch (EcoDataException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private EcoPlayer loadPlayerData(String uuid) throws EcoDataException {
        File playerFile = new File(dataFolder, uuid + ".yml");
        if (!playerFile.exists()) {
            EcoPlayer playerData = new EcoPlayer(uuid, getPlayerData(uuid).getBalance());
            savePlayerData(playerData);
            return playerData;
        }

        try (InputStream is = new FileInputStream(playerFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            double balance = 0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("balance:")) {
                    balance = AmountParser.parseAmount(line.substring(line.indexOf(':') + 1).trim());
                    break;
                }
            }

            return new EcoPlayer(uuid, balance);
        } catch (IOException e) {
            throw new EcoDataException(ColorUtils.translate("Failed to load player data: " + uuid));
        }
    }

    /**
     * Saves the player data.
     *
     * @param playerData the EcoPlayer object to be saved
     */
    public synchronized void savePlayerData(EcoPlayer playerData) throws EcoDataException {
        File playerFile = new File(dataFolder, playerData.getUuid() + ".yml");

        try (PrintWriter writer = new PrintWriter(playerFile)) {
            writer.println("balance: " + playerData.getBalance());
        } catch (FileNotFoundException e) {
            throw new EcoDataException(ColorUtils.translate("Failed to save player data: " + playerData.getUuid()));
        }
    }
}
