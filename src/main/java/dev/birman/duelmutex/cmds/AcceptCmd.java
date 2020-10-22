package dev.birman.duelmutex.cmds;

import dev.birman.duelmutex.Utils;
import dev.birman.duelmutex.api.DuelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (DuelManager.getDuelManager().hasDuelRequest(player)) {
                Player opponent = DuelManager.getDuelManager().getDuelPartner(player, true);
                DuelManager.getDuelManager().acceptDuelRequest(player);
                player.sendMessage(Utils.format("&aYou have accepted the duel request!"));
                opponent.sendMessage(Utils.format("&aThey have accepted the duel request!"));
            } else {
                player.sendMessage(Utils.format("&cYou do not have an active duel request."));
            }
        } else {
            sender.sendMessage(Utils.format("&cSorry, only players can use this command."));
        }
        return true;
    }
}
