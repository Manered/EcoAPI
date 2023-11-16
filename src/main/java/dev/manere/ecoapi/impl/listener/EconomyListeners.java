package dev.manere.ecoapi.impl.listener;

import dev.manere.ecoapi.init.Economy;
import dev.manere.ecoapi.impl.EconomyPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Implementation of the {@link Listener} interface for handling player join and quit events.
 */
public class EconomyListeners implements Listener {
    /**
     * Handles the player join event by caching the player data if not already cached.
     *
     * @param event The PlayerJoinEvent.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (Economy.caching().load(event.getPlayer().getUniqueId()) == null) {
            EconomyPlayer.of(event.getPlayer().getUniqueId())
                    .balance(0.0)
                    .uuid(event.getPlayer().getUniqueId())
                    .cache();
        }
    }

    /**
     * Handles the player quit event by caching the player data if not already cached.
     *
     * @param event The PlayerQuitEvent.
     */
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        EconomyPlayer economyPlayer = Economy.caching().load(event.getPlayer().getUniqueId());

        if (economyPlayer == null) {
            return;
        }

        economyPlayer.cache();
    }
}
