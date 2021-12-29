package cz.hopperw12.discordsync.user;

import cz.hopperw12.discordsync.DiscordSync;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private YamlConfiguration cfg;
    private DiscordSync main;

    public UserManager() {
        main = DiscordSync.getInstance();
        File file = new File(main.getDataFolder(), "users.yml");

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
        List<RegisteredUser> users = (List<RegisteredUser>) cfg.get("players");

        if (users == null)
            users = new ArrayList<>();

        users.add(user);
        cfg.set("players", users);
    }

    @SuppressWarnings("unchecked")
    public void unregisterUser(RegisteredUser user) {
        List<RegisteredUser> users = (List<RegisteredUser>) cfg.get("players");

        if (users == null)
            users = new ArrayList<>();

        users.remove(user);
        cfg.set("players", users);
    }

    @SuppressWarnings("unchecked")
    public boolean isRegistered(Player player) {
        List<RegisteredUser> users = (List<RegisteredUser>) cfg.get("players");

        if (users == null)
            return false;

        return users.stream()
                .anyMatch(u -> u.getMinecraftUUID().equals(player.getUniqueId()));
    }

    @SuppressWarnings("unchecked")
    public RegisteredUser get(Player player) {
        List<RegisteredUser> users = (List<RegisteredUser>) cfg.get("players");

        if (users == null)
            return null;

        return users.stream()
                .filter(u -> u.getMinecraftUUID().equals(player.getUniqueId()))
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public List<RegisteredUser> getAll() {
        List<RegisteredUser> users = (List<RegisteredUser>) cfg.get("players");

        if (users == null)
            users = new ArrayList<>();

        return users;
    }
}
