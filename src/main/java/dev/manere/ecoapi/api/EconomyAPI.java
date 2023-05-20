package dev.manere.ecoapi.api;

import dev.manere.ecoapi.data.EconomyManager;
import dev.manere.ecoapi.exceptions.EconomyDataException;
import dev.manere.ecoapi.exceptions.InsufficientFundsException;
import dev.manere.ecoapi.exceptions.InvalidAmountException;
import dev.manere.ecoapi.model.PlayerData;
import dev.manere.ecoapi.util.AmountParser;

public class EconomyAPI {
    private final EconomyManager economyManager;

    public EconomyAPI() {
        this.economyManager = new EconomyManager();
    }

    public double getBalance(String uuid) throws EconomyDataException {
        PlayerData playerData = economyManager.getPlayerData(uuid);
        return playerData.getBalance();
    }

    public void setBalance(String uuid, double balance) throws EconomyDataException {
        PlayerData playerData = economyManager.getPlayerData(uuid);
        playerData.setBalance(balance);
        economyManager.savePlayerData(playerData);
    }

    public void deposit(String uuid, String amount) throws InvalidAmountException, EconomyDataException {
        double parsedAmount = AmountParser.parseAmount(amount);
        if (parsedAmount <= 0) {
            throw new InvalidAmountException("Invalid deposit amount: " + amount);
        }

        PlayerData playerData = economyManager.getPlayerData(uuid);
        double newBalance = playerData.getBalance() + parsedAmount;
        playerData.setBalance(newBalance);
        economyManager.savePlayerData(playerData);
    }

    public void withdraw(String uuid, String amount) throws InvalidAmountException, InsufficientFundsException, EconomyDataException {
        double parsedAmount = AmountParser.parseAmount(amount);
        if (parsedAmount <= 0) {
            throw new InvalidAmountException("Invalid withdrawal amount: " + amount);
        }

        PlayerData playerData = economyManager.getPlayerData(uuid);
        double currentBalance = playerData.getBalance();
        if (parsedAmount > currentBalance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal: " + amount);
        }

        double newBalance = currentBalance - parsedAmount;
        playerData.setBalance(newBalance);
        economyManager.savePlayerData(playerData);
    }

    public void transfer(String senderUuid, String recipientUuid, String amount)
            throws InvalidAmountException, InsufficientFundsException, EconomyDataException {
        double parsedAmount = AmountParser.parseAmount(amount);
        if (parsedAmount <= 0) {
            throw new InvalidAmountException("Invalid transfer amount: " + amount);
        }

        PlayerData senderData = economyManager.getPlayerData(senderUuid);
        double senderBalance = senderData.getBalance();
        if (parsedAmount > senderBalance) {
            throw new InsufficientFundsException("Insufficient funds for transfer: " + amount);
        }

        PlayerData recipientData = economyManager.getPlayerData(recipientUuid);

        double senderNewBalance = senderBalance - parsedAmount;
        senderData.setBalance(senderNewBalance);
        economyManager.savePlayerData(senderData);

        double recipientNewBalance = recipientData.getBalance() + parsedAmount;
        recipientData.setBalance(recipientNewBalance);
        economyManager.savePlayerData(recipientData);
    }
}
