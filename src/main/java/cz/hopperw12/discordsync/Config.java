package cz.hopperw12.discordsync;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private FileConfiguration cfg;

    public Config() {
        DiscordSync main = DiscordSync.getInstance();
        main.saveDefaultConfig();
        this.cfg = main.getConfig();
    }

    public String getString(String path) {
        return cfg.getString(path);
    }

}
