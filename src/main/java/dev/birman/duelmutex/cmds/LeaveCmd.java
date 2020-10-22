package dev.birman.duelmutex.cmds;

import dev.birman.duelmutex.Utils;
import dev.birman.duelmutex.api.DuelManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (DuelManager.getDuelManager().isInDuel(player)) {
                Player opponent = DuelManager.getDuelManager().getDuelPartner(player, false);
                player.getInventory().remove(Material.IRON_SWORD);
                opponent.getInventory().remove(Material.IRON_SWORD);
                DuelManager.getDuelManager().endDuel(player);
                player.sendMessage(Utils.format("&aYou have left the duel."));
                opponent.sendMessage(Utils.format("&aYour opponent has left the duel."));
            }
        } else {
            sender.sendMessage(Utils.format("&cSorry, only players can use this command."));
        }
        return true;
    }
}
