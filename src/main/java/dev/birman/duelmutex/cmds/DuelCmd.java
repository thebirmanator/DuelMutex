package dev.birman.duelmutex.cmds;

import dev.birman.duelmutex.Utils;
import dev.birman.duelmutex.api.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                Player opponent = Bukkit.getPlayer(args[0]);
                if (opponent != null) {
                    Player player = (Player) sender;
                    if (!opponent.equals(player)) {
                        if (!DuelManager.getDuelManager().isInDuel(player)) {
                            DuelManager.getDuelManager().sendDuelRequest(player, opponent);
                        } else {
                            player.sendMessage(Utils.format("&cYou are already in a duel."));
                        }
                    } else {
                        player.sendMessage(Utils.format("&cYou cannot duel against yourself!"));
                    }
                }
            } else {
                sender.sendMessage(Utils.format("&cPlease provide a player to duel against."));
            }
        } else {
            sender.sendMessage(Utils.format("&cSorry, only players can use this command."));
        }
        return true;
    }
}
