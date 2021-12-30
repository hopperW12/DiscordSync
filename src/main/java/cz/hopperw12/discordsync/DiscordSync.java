package cz.hopperw12.discordsync;

import cz.hopperw12.discordsync.commads.CommandLink;
import cz.hopperw12.discordsync.discord.Bot;
import cz.hopperw12.discordsync.events.PlayerJoinListener;
import cz.hopperw12.discordsync.events.PlayerQuitListener;
import cz.hopperw12.discordsync.requests.RequestManager;
import cz.hopperw12.discordsync.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

public final class DiscordSync extends JavaPlugin {

    private static DiscordSync instance;

    public RequestManager requestManager;
    public UserManager userManager;
    public Config config;
    public Bot bot;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();

        config = new Config();

        requestManager = new RequestManager();
        userManager = new UserManager();

        bot = new Bot(config.getString("token"));

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
        manager.registerEvents(new PlayerJoinListener(), this);
    }

    public static DiscordSync getInstance() {
        return instance;
    }
}
