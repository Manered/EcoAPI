package dev.manere.economy;

import java.util.UUID;

public class EconomyPlayer {

    private final UUID playerId;
    private double balance;

    public EconomyPlayer(UUID playerId, double balance) {
        this.playerId = playerId;
        this.balance = balance;
    }

    public EconomyPlayer(UUID playerId, int balance) {
        this(playerId, (double) balance);
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}