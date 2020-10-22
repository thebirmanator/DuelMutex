package dev.birman.duelmutex.listeners;

import dev.birman.duelmutex.Main;
import dev.birman.duelmutex.Utils;
import dev.birman.duelmutex.api.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        if (DuelManager.getDuelManager().isInDuel(victim)) {
            // Player died in a duel
            event.setCancelled(true);
            Player winner = DuelManager.getDuelManager().getDuelPartner(victim, false);
            winner.sendMessage(Utils.format("&aYou have won the duel!"));
            victim.sendMessage(Utils.format("&cYou have lost the duel."));
            winner.getInventory().clear();
            victim.getInventory().clear();
            DuelManager.getDuelManager().endDuel(victim);
            // Send them away
            Location location = new Location(winner.getWorld(), 174, 72, 0);
            winner.teleport(location);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> victim.teleport(location));
        }
    }
}
