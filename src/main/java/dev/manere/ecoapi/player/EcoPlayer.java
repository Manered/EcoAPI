package dev.manere.ecoapi.player;

public class EcoPlayer {
    private final String uuid;
    private double balance;
    /**
     * Constructs an instance of PlayerData.
     *
     * @param uuid the UUID of the player
     */
    public EcoPlayer(String uuid, double balance) {
        this.uuid = uuid;
        this.balance = balance;
    }

    /**
     * Retrieves the UUID of the player.
     *
     * @return the UUID of the player
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Retrieves the balance of the player.
     *
     * @return the balance of the player
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the player.
     *
     * @param balance the new balance of the player
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
