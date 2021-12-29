package cz.hopperw12.discordsync.user;

import cz.hopperw12.discordsync.DiscordSync;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class UserManager {

    private YamlConfiguration cfg;

    public UserManager() {
        File file = new File(DiscordSync.getInstance().getDataFolder(), "users.yml");

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cfg = YamlConfiguration.loadConfiguration(file);
    }
}
