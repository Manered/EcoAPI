package dev.manere.economy;

import dev.manere.economy.commands.BalanceCommand;
import dev.manere.economy.persistence.PersistenceManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public class EconomyAPIPlugin extends JavaPlugin {

    private EconomyManager economyManager;
    private PersistenceManager persistenceManager;
    private EconomyPlaceholderHook economyPlaceholderHook;

    @Override
    public void onEnable() {
        // Initialize the plugin and persistence manager
        persistenceManager = new PersistenceManager(this);

        // Load all player data from YML files
        File playersFolder = new File(this.getDataFolder(), "players");
        if (playersFolder.exists()) {
            for (File playerFile : playersFolder.listFiles()) {
                String fileName = playerFile.getName();
                if (fileName.endsWith(".yml")) {
                    String playerIdStr = fileName.substring(0, fileName.length() - 4);
                    UUID playerId = UUID.fromString(playerIdStr);
                    persistenceManager.loadPlayerData(playerId);
                }
            }
        }

        economyPlaceholderHook = new EconomyPlaceholderHook(this);
        economyPlaceholderHook.register();

        // Register command and event listeners
        getCommand("balance").setExecutor(new BalanceCommand(this));
    }

    @Override
    public void onDisable() {
        economyPlaceholderHook.unregister();
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }
}