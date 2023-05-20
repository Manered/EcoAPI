package dev.manere.economy.listeners;

import dev.manere.economy.persistence.PersistenceManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final PersistenceManager persistenceManager;

    public PlayerJoinListener(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Set balance, which then creates the players data file.
        persistenceManager.setBalance(playerId, 0.0);
    }
}
