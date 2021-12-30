package cz.hopperw12.discordsync.user;

import cz.hopperw12.discordsync.DiscordSync;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
    }

    @SuppressWarnings("unchecked")
    public void registerUser(RegisteredUser user) {
        String path = String.format("players.%s", user.getPlayerName());

        cfg.set(path + ".minecraftUUID", user.getMinecraftUUID());
        cfg.set(path + ".discordUUID", user.getDiscordUUID());
        cfg.set(path + ".lastOnline", user.getLastOnline());

        save();
    }

    @SuppressWarnings("unchecked")
    public void unregisterUser(RegisteredUser user) {
        String path = String.format("players.%s", user.getPlayerName());

        cfg.set(path, null);

        save();
    }

    @SuppressWarnings("unchecked")
    public boolean isRegistered(Player player) {
        String path = String.format("players.%s", player.getName());

        return cfg.isSet(path);
    }

    public RegisteredUser get(Player player) {
        return get(player.getName());
    }

    @SuppressWarnings("unchecked")
    public RegisteredUser get(String playerName) {
        String path = String.format("players.%s", playerName);

        if (!cfg.isSet(path))
            return null;

        String minecraftUUID = cfg.getString(path + ".minecraftUUID");
        long discordUUID = cfg.getLong(path + ".discordUUID");
        long lastOnline = cfg.getLong(path + ".lastOnline");

        RegisteredUser user = new RegisteredUser(UUID.fromString(minecraftUUID), discordUUID);
        user.setLastOnline(lastOnline);

        return user;
    }

    @SuppressWarnings("unchecked")
    public List<RegisteredUser> getAll() {
        if (!cfg.isConfigurationSection("players"))
            return Collections.emptyList();

        return cfg.getConfigurationSection("players")
                .getKeys(false)
                .stream()
                .map(this::get)
                .collect(Collectors.toList());
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
