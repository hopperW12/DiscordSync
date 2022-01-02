package cz.hopperw12.discordsync;

import cz.hopperw12.discordsync.commads.CommandLink;
import cz.hopperw12.discordsync.commads.CommandUnlink;
import cz.hopperw12.discordsync.discord.Bot;
import cz.hopperw12.discordsync.listeners.*;
import cz.hopperw12.discordsync.requests.RequestManager;
import cz.hopperw12.discordsync.tabcompleter.UnlinkTabCompleter;
import cz.hopperw12.discordsync.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordSync extends JavaPlugin {

    private static DiscordSync instance;

    public RequestManager requestManager;
    public UserManager userManager;
    public Bot bot;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerCommands();
        registerEvents();

        requestManager = new RequestManager();
        userManager = new UserManager();

        bot = new Bot(getConfig().getString("bot.token"));
        bot.build();
    }

    @Override
    public void onDisable() {
        bot.shutdown();
        userManager.save();
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("link").setExecutor(new CommandLink());

        getCommand("unlink").setExecutor(new CommandUnlink());
        getCommand("unlink").setTabCompleter(new UnlinkTabCompleter());
    }

    private void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new PlayerQuitListener(), this);
        manager.registerEvents(new PlayerJoinListener(), this);
        manager.registerEvents(new AuthMeLoginListener(), this);

        manager.registerEvents(new PlayerListener(), this);
        manager.registerEvents(new BlockListener(), this);
        manager.registerEvents(new EntityListener(), this);

        manager.registerEvents(new UserRegisterListener(), this);
        manager.registerEvents(new UserUnregisterListener(), this);
    }

    public static DiscordSync getInstance() {
        return instance;
    }
}
