package dev.manere.ecoapi.api;

import dev.manere.ecoapi.impl.EconomyPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for handling economic transactions and operations.
 */
public interface EconomyHandler {
    /**
     * Transfers currency from one player to another.
     *
     * @param playerOne The player to transfer currency from.
     * @param playerTwo The player to transfer currency to.
     * @param amount    The amount of currency to transfer.
     */
    void transfer(@NotNull EconomyPlayer playerOne, @NotNull EconomyPlayer playerTwo, double amount);

    /**
     * Subtracts currency from a player.
     *
     * @param player The player from whom currency will be subtracted.
     * @param amount The amount of currency to subtract.
     */
    void subtract(@NotNull EconomyPlayer player, double amount);

    /**
     * Adds currency to a player.
     *
     * @param player The player to whom currency will be added.
     * @param amount The amount of currency to add.
     */
    void add(@NotNull EconomyPlayer player, double amount);

    /**
     * Performs a specified economic action on a player.
     *
     * @param player The player on whom the action will be performed.
     * @param action The economic action to perform.
     * @param amount The amount of currency involved in the action.
     */
    void action(@NotNull EconomyPlayer player, @NotNull EconomyAction action, double amount);

    /**
     * Sets the balance of a player to a new value.
     *
     * @param player     The player whose balance will be set.
     * @param newBalance The new balance to set.
     */
    void balance(@NotNull EconomyPlayer player, double newBalance);

    /**
     * Resets the balance of a player to the default value.
     *
     * @param player The player whose balance will be reset.
     */
    void reset(@NotNull EconomyPlayer player);
}
