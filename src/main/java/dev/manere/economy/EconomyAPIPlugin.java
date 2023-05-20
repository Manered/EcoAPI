package dev.manere.economy;

import dev.manere.economy.bstats.Metrics;
import dev.manere.economy.commands.BalanceCommand;
import dev.manere.economy.persistence.PersistenceManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public class EconomyAPIPlugin extends JavaPlugin {
    private PersistenceManager persistenceManager;
    private EconomyPlaceholderHook economyPlaceholderHook;

    /**
     * Called when the plugin is enabled.
     * Initializes the plugin and persistence manager, loads player data, and registers command and event listeners.
     */
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

        int pluginId = 18515; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        economyPlaceholderHook = new EconomyPlaceholderHook(this);
        economyPlaceholderHook.register();

        // Register command and event listeners
        getCommand("balance").setExecutor(new BalanceCommand(this));
    }

    /**
     * Called when the plugin is disabled.
     * Unregisters the economy placeholder hook.
     */
    @Override
    public void onDisable() {
        economyPlaceholderHook.unregister();
    }

    /**
     * Retrieves the persistence manager instance.
     * @return the persistence manager instance
     */
    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }
}
