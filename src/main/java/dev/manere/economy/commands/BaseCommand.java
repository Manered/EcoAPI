package dev.manere.economy.commands;

import dev.manere.utils.Color;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class BaseCommand implements CommandExecutor {
    private final CommandInfo commandInfo;

    public BaseCommand() {
        commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(commandInfo, "Commands require CommandInfo annotations");
    }

    public CommandInfo getCommand() {
        return commandInfo;
    }

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

    public void execute(Player player, String[] args) {
    }

    public void execute(CommandSender sender, String[] args) {
    }
}
