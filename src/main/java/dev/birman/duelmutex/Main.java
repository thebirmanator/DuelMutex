package dev.birman.duelmutex;

import dev.birman.duelmutex.cmds.AcceptCmd;
import dev.birman.duelmutex.cmds.DenyCmd;
import dev.birman.duelmutex.cmds.DuelCmd;
import dev.birman.duelmutex.cmds.LeaveCmd;
import dev.birman.duelmutex.listeners.PlayerDeathListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    /*
    Players can request to duel other players if they are in idle state
    Player can only accept the most recent one
    Simulate cross-server response time (add 1 second delay)
     */
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("duel").setExecutor(new DuelCmd());
        getCommand("accept").setExecutor(new AcceptCmd());
        getCommand("deny").setExecutor(new DenyCmd());
        getCommand("leave").setExecutor(new LeaveCmd());

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getConsoleSender().sendMessage(Utils.format("&aDuelMutex enabled!"));
    }

    public static Main getInstance() {
        return instance;
    }
}
