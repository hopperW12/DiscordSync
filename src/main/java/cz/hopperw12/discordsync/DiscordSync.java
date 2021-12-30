package cz.hopperw12.discordsync;

import cz.hopperw12.discordsync.commads.CommandLink;
import cz.hopperw12.discordsync.events.PlayerQuitListener;
import cz.hopperw12.discordsync.requests.RequestManager;
import cz.hopperw12.discordsync.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordSync extends JavaPlugin {

    private static DiscordSync instance;

    public RequestManager requestManager;
    public UserManager userManager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();

        //TODO - setup bot

        requestManager = new RequestManager();
        userManager = new UserManager();

    }

    @Override
    public void onDisable() {
        userManager.save();
    }


    private void registerCommands() {

        getCommand("link").setExecutor(new CommandLink());

    }


    private void registerEvents() {

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerQuitListener(), this);
    }

    public static DiscordSync getInstance() {
        return instance;
    }
}
