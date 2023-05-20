package dev.manere.economy.commands;

import dev.manere.economy.EconomyAPIPlugin;
import dev.manere.utils.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A command implementation for checking the balance of a player.
 * This command can be executed by both players and command senders.
 */
@CommandInfo(name = "balance", permission = "economy.balance", onlyPlayersCanExecute = false)
public class BalanceCommand extends BaseCommand {

    private final EconomyAPIPlugin plugin;

    /**
     * Constructs a BalanceCommand instance.
     * @param plugin the EconomyAPIPlugin instance
     */
    public BalanceCommand(EconomyAPIPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Executes the balance command for a command sender.
     * @param sender the CommandSender who executed the command
     * @param args the arguments provided with the command
     */
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
                sender.sendMessage(Color.translate("#fa0a26Player not found."));
                return;
            }

            plugin.getPersistenceManager().loadPlayerData(targetPlayer.getUniqueId());
            double balance = plugin.getPersistenceManager().getPlayerData(targetPlayer.getUniqueId()).getBalance();
            String formattedBalance = formatBalance(balance);

            sender.sendMessage(Color.translate("#00ff00&l$ &f" + targetPlayer.getName() + "'s Balance&7: #00ff00" + formattedBalance));
        } else {
            sender.sendMessage(Color.translate("#fa0a26Usage: /balance [player]"));
        }
    }

    /**
     * Formats the balance value into a string representation.
     * @param balance the balance value
     * @return the formatted balance string
     */
    private String formatBalance(double balance) {
        return String.format("$%.2f", balance);
    }
}
