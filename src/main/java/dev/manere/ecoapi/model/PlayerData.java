package dev.manere.ecoapi.model;

public class PlayerData {
    private final String uuid;
    private double balance;

    public PlayerData(String uuid, double balance) {
        this.uuid = uuid;
        this.balance = balance;
    }

    public String getUuid() {
        return uuid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
