package dev.manere.ecoapi.init;

import dev.manere.ecoapi.impl.listener.EconomyListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class for initializing the economy system.
 */
public class EconomyInitializer {
    private static JavaPlugin plugin;

    /**
     * Constructs an EconomyInitializer with the specified plugin.
     *
     * @param plugin The JavaPlugin associated with the economy system.
     */
    public EconomyInitializer(JavaPlugin plugin) {
        EconomyInitializer.plugin = plugin;
    }

    /**
     * Constructs an EconomyInitializer with no associated plugin.
     */
    public EconomyInitializer() {
        EconomyInitializer.plugin = null;
    }

    /**
     * Registers the EconomyListeners and loads player data when starting the economy system.
     *
     * @return The initialized EconomyInitializer.
     */
    public EconomyInitializer start() {
        Bukkit.getPluginManager().registerEvents(new EconomyListeners(), plugin());
        Economy.caching().loadAll();
        return this;
    }

    /**
     * Saves all player data when stopping the economy system.
     *
     * @return The initialized EconomyInitializer.
     */
    public EconomyInitializer stop() {
        Economy.caching().saveAll();
        return this;
    }

    /**
     * Executes the specified event after starting the economy system.
     *
     * @param executeAfterStart The event to execute.
     * @return The initialized EconomyInitializer.
     */
    public EconomyInitializer then(EconomyStartupEvent executeAfterStart) {
        executeAfterStart.onStartup(plugin(), economy());
        return this;
    }

    /**
     * Executes the specified event asynchronously after starting the economy system.
     *
     * @param executeAfterStart The event to execute asynchronously.
     * @return The initialized EconomyInitializer.
     */
    public EconomyInitializer thenAsync(EconomyStartupEvent executeAfterStart) {
        Bukkit.getScheduler().runTaskAsynchronously(EconomyInitializer.plugin(), () -> executeAfterStart.onStartup(plugin(), economy()));
        return this;
    }

    /**
     * Gets the associated plugin.
     *
     * @return The associated JavaPlugin.
     */
    public static JavaPlugin plugin() {
        return plugin;
    }

    /**
     * Creates a new instance of the Economy class.
     *
     * @return A new instance of Economy.
     */
    public Economy economy() {
        return new Economy();
    }

    // Private method to avoid instantiation of utility class
    private void a() {
        // this method does nothing. It is to avoid warning: Instantiation of utility class
    }
}
