package cz.hopperw12.discordsync.user;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.events.UserRegisterEvent;
import cz.hopperw12.discordsync.events.UserUnregisterEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserManager {
    private YamlConfiguration cfg;
    private DiscordSync main;
    private File file;

    public UserManager() {
        main = DiscordSync.getInstance();
        file = new File(main.getDataFolder(), "users.yml");

        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            cfg = YamlConfiguration.loadConfiguration(file);

        } catch (Exception e) {
            main.getLogger().severe("Failed to load users.yml");
            e.printStackTrace();
        }

        long checkPeriod = main.getConfig().getLong("timings.user.check");
        checkPeriod *= 60 * 20;

        Bukkit.getScheduler().runTaskTimer(main, this::unregisterExpired, 0L, checkPeriod);
    }

    public void registerUser(RegisteredUser user) {
        updateUser(user);

        UserRegisterEvent registerEvent = new UserRegisterEvent(user);
        Bukkit.getPluginManager().callEvent(registerEvent);

        save();
    }

    public void updateUser(RegisteredUser user) {
        String path = String.format("players.%s", user.getMinecraftUUID());

        cfg.set(path + ".minecraft.username", user.getPlayerName());
        cfg.set(path + ".minecraft.lastOnline", user.getLastOnline());
        cfg.set(path + ".discord.uuid", user.getDiscordUUID());

        path = String.format("links.%s", user.getDiscordUUID());
        cfg.set(path, user.getMinecraftUUID().toString());

        save();
    }

    public void unregisterUser(OfflinePlayer player) {
        unregisterUser(player.getUniqueId());
    }

    public void unregisterUser(UUID minecraftUUID) {
        String path = String.format("players.%s", minecraftUUID);
        long discordUUID = cfg.getLong(path + ".discord.uuid");

        unregisterUser(new RegisteredUser(minecraftUUID, discordUUID));
    }

    public void unregisterUser(long discordUUID) {
        String path = String.format("links.%s", discordUUID);

        if (!cfg.isSet(path))
            return;

        String minecraftUUID = cfg.getString(path);
        if (minecraftUUID == null) return;

        unregisterUser(new RegisteredUser(UUID.fromString(minecraftUUID), discordUUID));
    }

    public void unregisterUser(RegisteredUser user) {
        String path = String.format("players.%s", user.getMinecraftUUID());
        cfg.set(path, null);

        path = String.format("links.%s", user.getDiscordUUID());
        cfg.set(path, null);

        UserUnregisterEvent unregisterEvent = new UserUnregisterEvent(user);
        Bukkit.getPluginManager().callEvent(unregisterEvent);

        save();
    }

    public boolean isRegistered(OfflinePlayer player) {
        String path = String.format("players.%s", player.getUniqueId());

        return cfg.isSet(path);
    }

    public RegisteredUser get(OfflinePlayer player) {
        return get(player.getUniqueId());
    }

    public RegisteredUser get(long discordUUID) {
        String path = String.format("links.%s", discordUUID);

        if (!cfg.isSet(path))
            return null;

        String minecraftUUID = cfg.getString(path);
        if (minecraftUUID == null) return null;

        return get(UUID.fromString(minecraftUUID));
    }

    public RegisteredUser get(UUID minecraftUUID) {
        String path = String.format("players.%s", minecraftUUID);

        if (!cfg.isSet(path))
            return null;

        long discordUUID = cfg.getLong(path + ".discord.uuid");
        long lastOnline = cfg.getLong(path + ".minecraft.lastOnline");

        RegisteredUser user = new RegisteredUser(minecraftUUID, discordUUID);
        user.setLastOnline(lastOnline);

        return user;
    }

    public List<RegisteredUser> getAll() {
        if (!cfg.isConfigurationSection("players"))
            return Collections.emptyList();

        return cfg.getConfigurationSection("players")
                .getKeys(false)
                .stream()
                .map(s -> get(UUID.fromString(s)))
                .collect(Collectors.toList());
    }

    public void unregisterExpired() {
        long expiration = main.getConfig().getLong("timings.user.expiration");
        expiration *= 60 * 1000;

        List<RegisteredUser> users = getAll();

        for (RegisteredUser user : users) {
            OfflinePlayer offlinePlayer = user.getOfflinePlayer();

            if (offlinePlayer.isOnline())
                continue;

            if (user.getLastOnline() + expiration < System.currentTimeMillis())
                unregisterUser(user);
        }
    }

    public void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            main.getLogger().severe("Failed to save users.yml");
            e.printStackTrace();
        }
    }
}
