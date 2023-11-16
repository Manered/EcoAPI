package dev.manere.ecoapi.init;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for events to be executed during the startup of the economy system.
 */
public interface EconomyStartupEvent {
    /**
     * Executes the event during the startup of the economy system.
     *
     * @param plugin  The JavaPlugin associated with the economy system.
     * @param economy The Economy instance.
     */
    void onStartup(@NotNull JavaPlugin plugin, @NotNull Economy economy);
}
