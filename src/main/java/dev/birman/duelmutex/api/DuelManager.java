package dev.birman.duelmutex.api;

import dev.birman.duelmutex.Main;
import dev.birman.duelmutex.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DuelManager {

    // key is sender
    private Map<Player, Player> playersInDuel;
    // key is receiver
    private Map<Player, Player> requestingDuels;

    private static DuelManager instance;
    // delay in ticks
    private int delay = 20;

    private DuelManager() {
        playersInDuel = new HashMap<>();
        requestingDuels = new HashMap<>();
    }

    public boolean isInDuel(Player player) {
        return playersInDuel.containsKey(player) || playersInDuel.containsValue(player);
    }

    public void sendDuelRequest(Player sender, Player opponent) {
        sender.sendMessage(Utils.format("&aSent request to " + opponent.getName()));
        sendDuelMessage(0, sender, opponent);
    }

    public void acceptDuelRequest(Player receiver) {
        Player sender = requestingDuels.remove(receiver);
        sendDuelMessage(1, sender, receiver);
    }

    public void denyDuelRequest(Player receiver) {
        sendDuelMessage(2, getDuelPartner(receiver, true), receiver);
    }

    public boolean hasDuelRequest(Player player) {
        return requestingDuels.containsKey(player) || requestingDuels.containsValue(player);
    }

    public void endDuel(Player player) {
        if (playersInDuel.containsKey(player)) {
            // If we use the sender to end the duel
            playersInDuel.remove(player);
        } else {
            // If we use the receiver to end the duel
            playersInDuel.remove(getDuelPartner(player, false));
        }
    }

    public Player getDuelPartner(Player player, boolean isRequesting) {
        Map<Player, Player> duelType = playersInDuel;
        if (isRequesting) {
            duelType = requestingDuels;
        }
        if (duelType.containsKey(player)) {
            // If the player is the key
            return duelType.get(player);
        } else {
            // If the player is the value
            Set<Map.Entry<Player, Player>> entrySet = duelType.entrySet();
            for (Map.Entry<Player, Player> entry : entrySet) {
                if (entry.getValue().equals(player)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    private void sendDuelMessage(int type, Player sender, Player receiver) {
        // Representing sending a message cross server. The code inside would likely be part of its own listener(s)
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),() -> {
            switch (type) {
                case 0:
                    // Sending request
                    if (!isInDuel(receiver)) {
                        receiver.sendMessage(Utils.format(sender.getName() + " &ahas requested a duel! Type /accept to accept it."));
                        requestingDuels.put(receiver, sender);
                    }
                    break;
                case 1:
                    // Accepting request
                    playersInDuel.put(sender, receiver);
                    // Send players to same server to fight and give them equipment
                    Location spawnLoc = new Location(sender.getWorld(), 191, 74, 0, -90, 0);
                    sender.teleport(spawnLoc);
                    spawnLoc.setYaw(spawnLoc.getYaw() + 180);
                    receiver.teleport(spawnLoc.add(10, 0, 0));
                    receiver.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
                    sender.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
                    break;
                case 2:
                    // Denying request
                    requestingDuels.remove(receiver);
                    sender.sendMessage(Utils.format("&cThey have denied the duel request."));
                    break;
                default:
                    // Unknown state
                    Bukkit.getServer().getLogger().warning("Unknown duel message state read.");
                    break;
            }
        }, delay);
    }

    public static DuelManager getDuelManager() {
        if (instance == null) {
            instance = new DuelManager();
        }
        return instance;
    }
}
