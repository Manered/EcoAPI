package dev.manere.ecoapi.api;

import dev.manere.ecoapi.data.EconomyManager;
import dev.manere.ecoapi.exceptions.EconomyDataException;
import dev.manere.ecoapi.exceptions.InsufficientFundsException;
import dev.manere.ecoapi.exceptions.InvalidAmountException;
import dev.manere.ecoapi.model.PlayerData;
import dev.manere.ecoapi.util.AmountParser;
import dev.manere.ecoapi.util.ColorUtils;

/**
 * API for managing player economy.
 */
public class EconomyAPI {
    private final EconomyManager economyManager;

    /**
     * Constructs an instance of EconomyAPI.
     */
    public EconomyAPI() {
        this.economyManager = new EconomyManager();
    }

    /**
     * Retrieves the balance of a player.
     *
     * @param uuid the UUID of the player
     * @return the balance of the player
     * @throws EconomyDataException if there is an error retrieving the economy data
     */
    public double getBalance(String uuid) throws EconomyDataException {
        PlayerData playerData = economyManager.getPlayerData(uuid);
        return playerData.getBalance();
    }

    /**
     * Sets the balance of a player.
     *
     * @param uuid    the UUID of the player
     * @param balance the new balance of the player
     * @throws EconomyDataException if there is an error saving the economy data
     */
    public void setBalance(String uuid, double balance) throws EconomyDataException {
        PlayerData playerData = economyManager.getPlayerData(uuid);
        playerData.setBalance(balance);
        economyManager.savePlayerData(playerData);
    }

    /**
     * Deposits an amount to a player's balance.
     *
     * @param uuid   the UUID of the player
     * @param amount the amount to deposit
     * @throws InvalidAmountException  if the amount is invalid or negative
     * @throws EconomyDataException    if there is an error saving the economy data
     */
    public void deposit(String uuid, String amount) throws InvalidAmountException, EconomyDataException {
        double parsedAmount = AmountParser.parseAmount(amount);
        if (parsedAmount <= 0) {
            throw new InvalidAmountException(ColorUtils.translate("#ff0000Invalid deposit amount: " + amount));
        }

        PlayerData playerData = economyManager.getPlayerData(uuid);
        double newBalance = playerData.getBalance() + parsedAmount;
        playerData.setBalance(newBalance);
        economyManager.savePlayerData(playerData);
    }

    /**
     * Withdraws an amount from a player's balance.
     *
     * @param uuid   the UUID of the player
     * @param amount the amount to withdraw
     * @throws InvalidAmountException    if the amount is invalid or negative
     * @throws InsufficientFundsException if the player has insufficient funds for withdrawal
     * @throws EconomyDataException      if there is an error saving the economy data
     */
    public void withdraw(String uuid, String amount) throws InvalidAmountException, InsufficientFundsException, EconomyDataException {
        double parsedAmount = AmountParser.parseAmount(amount);
        if (parsedAmount <= 0) {
            throw new InvalidAmountException(ColorUtils.translate("#ff0000Invalid withdrawal amount: " + amount));
        }

        PlayerData playerData = economyManager.getPlayerData(uuid);
        double currentBalance = playerData.getBalance();
        if (parsedAmount > currentBalance) {
            throw new InsufficientFundsException(ColorUtils.translate("#ff0000Insufficient funds for withdrawal: " + amount));
        }

        double newBalance = currentBalance - parsedAmount;
        playerData.setBalance(newBalance);
        economyManager.savePlayerData(playerData);
    }

    /**
     * Transfers an amount from one player to another.
     *
     * @param senderUuid    the UUID of the sender player
     * @param recipientUuid the UUID of the recipient player
     * @param amount        the amount to transfer
     * @throws InvalidAmountException    if the amount is invalid or negative
     * @throws InsufficientFundsException if the sender player has insufficient funds for transfer
     * @throws EconomyDataException      if there is an error saving the economy data
     */
    public void transfer(String senderUuid, String recipientUuid, String amount) throws InvalidAmountException, InsufficientFundsException, EconomyDataException {
        double parsedAmount = AmountParser.parseAmount(amount);
        if (parsedAmount <= 0) {
            throw new InvalidAmountException(ColorUtils.translate("#ff0000Invalid transfer amount: " + amount));
        }

        PlayerData senderData = economyManager.getPlayerData(senderUuid);
        PlayerData recipientData = economyManager.getPlayerData(recipientUuid);

        double senderBalance = senderData.getBalance();
        if (parsedAmount > senderBalance) {
            throw new InsufficientFundsException(ColorUtils.translate("#ff0000Insufficient funds for transfer: " + amount));
        }

        double senderNewBalance = senderBalance - parsedAmount;
        senderData.setBalance(senderNewBalance);
        recipientData.setBalance(recipientData.getBalance() + parsedAmount);

        economyManager.savePlayerData(senderData);
        economyManager.savePlayerData(recipientData);
    }
}