package dev.manere.ecoapi.api;

import dev.manere.ecoapi.exceptions.EcoDataException;
import dev.manere.ecoapi.exceptions.InsufficientFundsException;
import dev.manere.ecoapi.exceptions.InvalidAmountException;
import dev.manere.ecoapi.managers.EcoManager;
import dev.manere.ecoapi.player.EcoPlayer;
import dev.manere.ecoapi.util.AmountParser;
import dev.manere.ecoapi.util.ColorUtils;

/**
 * API for managing player economy.
 */
public class EcoAPI {
    private final EcoManager ecoManager;

    /**
     * Constructs an instance of EcoAPI.
     */
    public EcoAPI() {
        this.ecoManager = new EcoManager();
    }

    /**
     * Retrieves the balance of a player.
     *
     * @param uuid the UUID of the player
     * @return the balance of the player
     * @throws EcoDataException if there is an error retrieving the economy data
     */
    public double getBalance(String uuid) throws EcoDataException {
        EcoPlayer playerData = ecoManager.getPlayerData(uuid);
        return playerData.getBalance();
    }

    /**
     * Sets the balance of a player.
     *
     * @param uuid    the UUID of the player
     * @param balance the new balance of the player
     * @throws EcoDataException if there is an error saving the economy data
     */
    public void setBalance(String uuid, double balance) throws EcoDataException {
        EcoPlayer playerData = ecoManager.getPlayerData(uuid);
        playerData.setBalance(balance);
        ecoManager.savePlayerData(playerData);
    }

    /**
     * Deposits an amount to a player's balance.
     *
     * @param uuid   the UUID of the player
     * @param amount the amount to deposit
     * @throws InvalidAmountException  if the amount is invalid or negative
     * @throws EcoDataException    if there is an error saving the economy data
     */
    public void deposit(String uuid, String amount) throws InvalidAmountException, EcoDataException {
        double parsedAmount = AmountParser.parseAmount(amount);
        if (parsedAmount <= 0) {
            throw new InvalidAmountException(ColorUtils.translate("Invalid deposit amount: " + amount));
        }

        EcoPlayer playerData = ecoManager.getPlayerData(uuid);
        double newBalance = playerData.getBalance() + parsedAmount;
        playerData.setBalance(newBalance);
        ecoManager.savePlayerData(playerData);
    }

    /**
     * Withdraws an amount from a player's balance.
     *
     * @param uuid   the UUID of the player
     * @param amount the amount to withdraw
     * @throws InvalidAmountException    if the amount is invalid or negative
     * @throws InsufficientFundsException if the player has insufficient funds for withdrawal
     * @throws EcoDataException      if there is an error saving the economy data
     */
    public void withdraw(String uuid, String amount) throws InvalidAmountException, InsufficientFundsException, EcoDataException {
        double parsedAmount = AmountParser.parseAmount(amount);
        if (parsedAmount <= 0) {
            throw new InvalidAmountException(ColorUtils.translate("Invalid withdrawal amount: " + amount));
        }

        EcoPlayer playerData = ecoManager.getPlayerData(uuid);
        double currentBalance = playerData.getBalance();
        if (parsedAmount > currentBalance) {
            throw new InsufficientFundsException(ColorUtils.translate("Insufficient funds for withdrawal: " + amount));
        }

        double newBalance = currentBalance - parsedAmount;
        playerData.setBalance(newBalance);
        ecoManager.savePlayerData(playerData);
    }

    /**
     * Transfers an amount from one player to another.
     *
     * @param senderUuid    the UUID of the sender player
     * @param recipientUuid the UUID of the recipient player
     * @param amount        the amount to transfer
     * @throws InvalidAmountException    if the amount is invalid or negative
     * @throws InsufficientFundsException if the sender player has insufficient funds for transfer
     * @throws EcoDataException      if there is an error saving the economy data
     */
    public void transfer(String senderUuid, String recipientUuid, String amount) throws InvalidAmountException, InsufficientFundsException, EcoDataException {
        double parsedAmount = AmountParser.parseAmount(amount);
        if (parsedAmount <= 0) {
            throw new InvalidAmountException(ColorUtils.translate("Invalid transfer amount: " + amount));
        }

        EcoPlayer senderData = ecoManager.getPlayerData(senderUuid);
        EcoPlayer recipientData = ecoManager.getPlayerData(recipientUuid);

        double senderBalance = senderData.getBalance();
        if (parsedAmount > senderBalance) {
            throw new InsufficientFundsException(ColorUtils.translate("Insufficient funds for transfer: " + amount));
        }

        double senderNewBalance = senderBalance - parsedAmount;
        senderData.setBalance(senderNewBalance);
        recipientData.setBalance(recipientData.getBalance() + parsedAmount);

        ecoManager.savePlayerData(senderData);
        ecoManager.savePlayerData(recipientData);
    }

    public EcoManager getEcoManager() {
        return ecoManager;
    }
}