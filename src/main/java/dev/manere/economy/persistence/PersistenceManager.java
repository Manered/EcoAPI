package dev.manere.economy.persistence;

import dev.manere.economy.EconomyAPIPlugin;
import dev.manere.economy.EconomyPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PersistenceManager {

    private final EconomyAPIPlugin plugin;
    private final File dataFolder;
    private final Map<UUID, EconomyPlayer> playerData;

    public PersistenceManager(EconomyAPIPlugin plugin) {
        this.plugin = plugin;
        this.dataFolder = plugin.getDataFolder();
        this.playerData = new HashMap<>();
    }

    public void savePlayerData(UUID playerId) {
        EconomyPlayer economyPlayer = getPlayerData(playerId);
        File playerFile = getPlayerDataFile(playerId);
        FileConfiguration playerConfig = new YamlConfiguration();

        ConfigurationSection playerSection = playerConfig.createSection("player");
        playerSection.set("balance", economyPlayer.getBalance());

        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save player data for UUID: " + playerId);
            e.printStackTrace();
        }
    }

    public void loadPlayerData(UUID playerId) {
        File playerFile = getPlayerDataFile(playerId);
        if (!playerFile.exists()) {
            return;
        }

        FileConfiguration playerConfig = new YamlConfiguration();
        try {
            playerConfig.load(playerFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().warning("Failed to load player data for UUID: " + playerId);
            e.printStackTrace();
            return;
        }

        ConfigurationSection playerSection = playerConfig.getConfigurationSection("player");
        if (playerSection == null) {
            return;
        }

        double balance = playerSection.getDouble("balance");

        EconomyPlayer economyPlayer = new EconomyPlayer(playerId, balance);
        playerData.put(playerId, economyPlayer);
    }

    public EconomyPlayer getPlayerData(UUID playerId) {
        EconomyPlayer economyPlayer = playerData.get(playerId);
        if (economyPlayer == null) {
            economyPlayer = new EconomyPlayer(playerId, 0);
            // Load player data from YML file
            loadPlayerData(playerId);
            // Update player data with balance from YML file
            File playerFile = getPlayerDataFile(playerId);
            FileConfiguration playerConfig = new YamlConfiguration();
            try {
                playerConfig.load(playerFile);
                ConfigurationSection playerSection = playerConfig.getConfigurationSection("player");
                if (playerSection != null) {
                    double balance = playerSection.getDouble("balance");
                    economyPlayer.setBalance(balance);
                }
            } catch (IOException | InvalidConfigurationException e) {
                plugin.getLogger().warning("Failed to load player data for UUID: " + playerId);
                e.printStackTrace();
            }
            playerData.put(playerId, economyPlayer);
        }
        return economyPlayer;
    }

    private File getPlayerDataFile(UUID playerId) {
        return new File(dataFolder, "players/" + playerId + ".yml");
    }

    public void deposit(UUID playerId, double amount) {
        EconomyPlayer economyPlayer = getPlayerData(playerId);
        double newBalance = economyPlayer.getBalance() + amount;
        economyPlayer.setBalance(newBalance);
        savePlayerData(playerId);
    }

    public void withdraw(UUID playerId, double amount) {
        EconomyPlayer economyPlayer = getPlayerData(playerId);
        double newBalance = economyPlayer.getBalance() - amount;
        economyPlayer.setBalance(newBalance);
        savePlayerData(playerId);
    }

    public void transferPlayer(UUID senderId, UUID receiverId, double amount) {
        EconomyPlayer sender = getPlayerData(senderId);
        EconomyPlayer receiver = getPlayerData(receiverId);
        double senderBalance = sender.getBalance();
        double receiverBalance = receiver.getBalance();
        if (senderBalance >= amount) {
            sender.setBalance(senderBalance - amount);
            receiver.setBalance(receiverBalance + amount);
            savePlayerData(senderId);
            savePlayerData(receiverId);
        } else {
            plugin.getLogger().warning("Transfer failed: sender does not have enough balance");
        }
    }

    public void setBalance(UUID playerId, double balance) {
        EconomyPlayer economyPlayer = getPlayerData(playerId);
        economyPlayer.setBalance(balance);
        savePlayerData(playerId);
    }

    public void addToBalance(UUID playerId, double amount) {
        EconomyPlayer economyPlayer = getPlayerData(playerId);
        double newBalance = economyPlayer.getBalance() + amount;
        economyPlayer.setBalance(newBalance);
        savePlayerData(playerId);
    }

    public void removeFromBalance(UUID playerId, double amount) {
        EconomyPlayer economyPlayer = getPlayerData(playerId);
        double newBalance = economyPlayer.getBalance() - amount;
        economyPlayer.setBalance(newBalance);
        savePlayerData(playerId);
    }

}