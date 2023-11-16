package dev.manere.ecoapi.impl;

import dev.manere.ecoapi.api.EconomyAction;
import dev.manere.ecoapi.api.EconomyHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of the {@link EconomyHandler} interface for handling economic transactions and operations.
 */
public class EconomyHandlerImpl implements EconomyHandler {
    /**
     * Transfers currency from one player to another.
     *
     * @param playerOne The player to transfer currency from.
     * @param playerTwo The player to transfer currency to.
     * @param amount    The amount of currency to transfer.
     */
    @Override
    public void transfer(@NotNull EconomyPlayer playerOne, @NotNull EconomyPlayer playerTwo, double amount) {
        playerOne.balance(playerOne.balance() - amount);
        playerTwo.balance(playerTwo.balance() + amount);

        playerOne.cache();
        playerTwo.cache();
    }

    /**
     * Subtracts currency from a player.
     *
     * @param player The player from whom currency will be subtracted.
     * @param amount The amount of currency to subtract.
     */
    @Override
    public void subtract(@NotNull EconomyPlayer player, double amount) {
        player.balance(player.balance() - amount);
        player.cache();
    }

    /**
     * Adds currency to a player.
     *
     * @param player The player to whom currency will be added.
     * @param amount The amount of currency to add.
     */
    @Override
    public void add(@NotNull EconomyPlayer player, double amount) {
        player.balance(player.balance() + amount);
        player.cache();
    }

    /**
     * Performs a specified economic action on a player.
     *
     * @param player The player on whom the action will be performed.
     * @param action The economic action to perform.
     * @param amount The amount of currency involved in the action.
     */
    @Override
    public void action(@NotNull EconomyPlayer player, @NotNull EconomyAction action, double amount) {
        switch (action) {
            case ADD -> add(player, amount);
            case SUBTRACT -> subtract(player, amount);
        }
    }

    /**
     * Sets the balance of a player to a new value.
     *
     * @param player     The player whose balance will be set.
     * @param newBalance The new balance to set.
     */
    @Override
    public void balance(@NotNull EconomyPlayer player, double newBalance) {
        player.balance(newBalance);
        player.cache();
    }

    /**
     * Sets the balance of a player to 0.0, which is the default.
     * @param player The player whose balance will be reset.
     */
    @Override
    public void reset(@NotNull EconomyPlayer player) {
        player.balance(0.0);
        player.cache();
    }
}
