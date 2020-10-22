package dev.birman.duelmutex.cmds;

import dev.birman.duelmutex.Utils;
import dev.birman.duelmutex.api.DuelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DenyCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (DuelManager.getDuelManager().hasDuelRequest(player)) {
                DuelManager.getDuelManager().denyDuelRequest(player);
                player.sendMessage(Utils.format("&aYou have denied the duel request."));
            } else {
                player.sendMessage(Utils.format("&cThere is no duel to deny."));
            }
        } else {
            sender.sendMessage(Utils.format("&cSorry, only players can use this command."));
        }
        return true;
    }
}
