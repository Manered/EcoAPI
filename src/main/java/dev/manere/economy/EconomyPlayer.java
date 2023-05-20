package dev.manere.economy;

import java.util.UUID;

public class EconomyPlayer {

    private final UUID playerId;
    private double balance;

    /**
     * Constructs a new EconomyPlayer instance.
     * @param playerId the UUID of the player
     * @param balance the initial balance of the player
     */
    public EconomyPlayer(UUID playerId, double balance) {
        this.playerId = playerId;
        this.balance = balance;
    }

    /**
     * Constructs a new EconomyPlayer instance with an integer balance.
     * @param playerId the UUID of the player
     * @param balance the initial balance of the player
     */
    public EconomyPlayer(UUID playerId, int balance) {
        this(playerId, (double) balance);
    }

    /**
     * Retrieves the UUID of the player.
     * @return the player's UUID
     */
    public UUID getPlayerId() {
        return playerId;
    }

    /**
     * Retrieves the balance of the player.
     * @return the player's balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the player.
     * @param balance the new balance of the player
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

}
