package dev.manere.economy.commands;

import dev.manere.economy.EconomyAPIPlugin;
import dev.manere.utils.Color;
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
                sender.sendMessage(Color.translate("#fa0a26Only players can execute /balance with no arguments."));
            } else {
                Player player = (Player) sender;
                plugin.getPersistenceManager().loadPlayerData(player.getUniqueId());
                double balance = plugin.getPersistenceManager().getPlayerData(player.getUniqueId()).getBalance();
                String formattedBalance = formatBalance(balance);

                sender.sendMessage(Color.translate("#00ff00&l$ &fBalance&7: #00ff00" + formattedBalance));
            }
        } else if (args.length == 1) {

            String playerName = args[0];
            Player targetPlayer = plugin.getServer().getPlayerExact(playerName);

            if (targetPlayer == null) {
                sender.sendMessage(Color.translate("Player not found."));
                return;
            }

            plugin.getPersistenceManager().loadPlayerData(targetPlayer.getUniqueId());
            double balance = plugin.getPersistenceManager().getPlayerData(targetPlayer.getUniqueId()).getBalance();
            String formattedBalance = formatBalance(balance);

            sender.sendMessage(Color.translate(targetPlayer.getName() + "'s Balance: " + formattedBalance));
        } else {
            sender.sendMessage(Color.translate("Usage: /balance [player]"));
        }
    }

    private String formatBalance(double balance) {
        return String.format("$%.2f", balance);
    }
}
