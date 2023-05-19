package dev.manere.economy;

import dev.manere.economy.persistence.PersistenceManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EconomyManager {

    private final PersistenceManager persistenceManager;

    public EconomyManager(EconomyAPIPlugin plugin) {
        this.persistenceManager = new PersistenceManager(plugin);
    }

    public void depositPlayer(Player player, double amount) {
        UUID playerId = player.getUniqueId();
        EconomyPlayer economyPlayer = persistenceManager.getPlayerData(playerId);

        double newBalance = economyPlayer.getBalance() + amount;
        economyPlayer.setBalance(newBalance);
        persistenceManager.savePlayerData(playerId);
    }

    public void withdrawPlayer(Player player, double amount) {
        UUID playerId = player.getUniqueId();
        EconomyPlayer economyPlayer = persistenceManager.getPlayerData(playerId);

        double newBalance = economyPlayer.getBalance() - amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("Not enough funds");
        }

        economyPlayer.setBalance(newBalance);
        persistenceManager.savePlayerData(playerId);
    }

    public void transferPlayer(Player fromPlayer, Player toPlayer, double amount) {
        UUID fromPlayerId = fromPlayer.getUniqueId();
        UUID toPlayerId = toPlayer.getUniqueId();

        EconomyPlayer fromEconomyPlayer = persistenceManager.getPlayerData(fromPlayerId);
        EconomyPlayer toEconomyPlayer = persistenceManager.getPlayerData(toPlayerId);

        double fromPlayerBalance = fromEconomyPlayer.getBalance();
        if (fromPlayerBalance < amount) {
            throw new IllegalArgumentException("Not enough funds");
        }

        double toPlayerBalance = toEconomyPlayer.getBalance();

        fromEconomyPlayer.setBalance(fromPlayerBalance - amount);
        toEconomyPlayer.setBalance(toPlayerBalance + amount);

        persistenceManager.savePlayerData(fromPlayerId);
        persistenceManager.savePlayerData(toPlayerId);
    }
}