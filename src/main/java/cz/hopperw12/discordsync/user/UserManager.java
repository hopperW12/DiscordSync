package cz.hopperw12.discordsync.user;

import cz.hopperw12.discordsync.DiscordSync;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
        Set<RegisteredUser> users = (Set<RegisteredUser>) cfg.get("players");

        if (users == null)
            users = new HashSet<>();

        users.remove(user);
        users.add(user);

        cfg.set("players", users);
    }

    @SuppressWarnings("unchecked")
    public void unregisterUser(RegisteredUser user) {
        Set<RegisteredUser> users = (Set<RegisteredUser>) cfg.get("players");

        if (users == null)
            users = new HashSet<>();

        users.remove(user);
        cfg.set("players", users);
    }

    @SuppressWarnings("unchecked")
    public boolean isRegistered(Player player) {
        Set<RegisteredUser> users = (Set<RegisteredUser>) cfg.get("players");

        if (users == null)
            return false;

        return users.stream()
                .anyMatch(u -> u.getMinecraftUUID().equals(player.getUniqueId()));
    }

    @SuppressWarnings("unchecked")
    public RegisteredUser get(Player player) {
        Set<RegisteredUser> users = (Set<RegisteredUser>) cfg.get("players");

        if (users == null)
            return null;

        return users.stream()
                .filter(u -> u.getMinecraftUUID().equals(player.getUniqueId()))
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public Set<RegisteredUser> getAll() {
        Set<RegisteredUser> users = (Set<RegisteredUser>) cfg.get("players");

        if (users == null)
            users = new HashSet<>();

        return users;
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
