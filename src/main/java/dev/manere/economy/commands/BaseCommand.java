package dev.manere.economy.commands;

import dev.manere.utils.Color;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This abstract class provides a base implementation for commands.
 * It implements the CommandExecutor interface and provides common functionality for executing commands.
 */
public abstract class BaseCommand implements CommandExecutor {
    private final CommandInfo commandInfo;

    /**
     * Constructs a BaseCommand instance.
     * It retrieves the CommandInfo annotation from the implementing class.
     * @throws NullPointerException if the implementing class does not have a CommandInfo annotation
     */
    public BaseCommand() {
        commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(commandInfo, "Commands require CommandInfo annotations");
    }

    /**
     * Retrieves the CommandInfo annotation associated with the command.
     * @return the CommandInfo annotation associated with the command
     */
    public CommandInfo getCommand() {
        return commandInfo;
    }

    /**
     * Executes the command when it is triggered by a command sender.
     * @param sender the CommandSender who executed the command
     * @param cmd the Command instance representing the executed command
     * @param label the alias used to execute the command
     * @param args the arguments provided with the command
     * @return true if the command was executed successfully, false otherwise
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!getCommand().permission().isEmpty()) {
            if (!sender.hasPermission(commandInfo.permission())) {
                sender.sendMessage(Color.translate("#fa0a26You don't have permission to execute this command."));
                return true;
            }
        }

        if (commandInfo.onlyPlayersCanExecute()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Color.translate("#fa0a26Only players can execute this command."));
                return true;
            }

            execute((Player) sender, args);
            return true;
        }

        execute(sender, args);
        return true;
    }

    /**
     * Executes the command for a player.
     * This method should be overridden in the implementing class to provide the command's specific behavior for players.
     * @param player the Player who executed the command
     * @param args the arguments provided with the command
     */
    public void execute(Player player, String[] args) {
    }

    /**
     * Executes the command for a command sender.
     * This method should be overridden in the implementing class to provide the command's specific behavior for command senders.
     * @param sender the CommandSender who executed the command
     * @param args the arguments provided with the command
     */
    public void execute(CommandSender sender, String[] args) {
    }
}
