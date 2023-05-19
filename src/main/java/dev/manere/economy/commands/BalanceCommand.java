package dev.manere.economy.commands;

import dev.manere.economy.EconomyAPIPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "balance", permission = "economy.balance", onlyPlayersCanExecute = false)
public class BalanceCommand extends BaseCommand {

    private final EconomyAPIPlugin plugin;

    public BalanceCommand(EconomyAPIPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can execute /balance with no arguments.");
            } else {
                Player player = (Player) sender;
                plugin.getPersistenceManager().loadPlayerData(player.getUniqueId());
                double balance = plugin.getPersistenceManager().getPlayerData(player.getUniqueId()).getBalance();
                String formattedBalance = formatBalance(balance);

                sender.sendMessage(ChatColor.WHITE + "Balance: " + ChatColor.translateAlternateColorCodes('&', formattedBalance));
            }
        } else if (args.length == 1) {

            String playerName = args[0];
            Player targetPlayer = plugin.getServer().getPlayerExact(playerName);

            if (targetPlayer == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return;
            }

            plugin.getPersistenceManager().loadPlayerData(targetPlayer.getUniqueId());
            double balance = plugin.getPersistenceManager().getPlayerData(targetPlayer.getUniqueId()).getBalance();
            String formattedBalance = formatBalance(balance);

            sender.sendMessage(ChatColor.WHITE + targetPlayer.getName() + "'s Balance: " + ChatColor.translateAlternateColorCodes('&', formattedBalance));
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /balance [player]");
        }
    }

    private String formatBalance(double balance) {
        return String.format("&a$%.2f", balance);
    }
}
