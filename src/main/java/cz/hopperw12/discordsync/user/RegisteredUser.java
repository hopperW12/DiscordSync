package cz.hopperw12.discordsync.user;

import cz.hopperw12.discordsync.DiscordSync;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class RegisteredUser {
    private UserManager userManager;

    private UUID minecraftUUID;
    private long discordUUID;
    private long lastOnline;

    public RegisteredUser(Player player, long discordUUID) {
        this(player.getUniqueId(), discordUUID);
    }

    public RegisteredUser(UUID minecraftUUID, long discordUUID) {
        this.minecraftUUID = minecraftUUID;
        this.discordUUID = discordUUID;

        this.userManager = DiscordSync.getInstance().userManager;
    }

    public UUID getMinecraftUUID() {
        return minecraftUUID;
    }

    public long getDiscordUUID() {
        return discordUUID;
    }

    public String getPlayerName() {
        return Bukkit.getOfflinePlayer(minecraftUUID).getName();
    }

    public long getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(long lastOnline) {
        this.lastOnline = lastOnline;
        userManager.registerUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisteredUser user = (RegisteredUser) o;
        return Objects.equals(minecraftUUID, user.minecraftUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(minecraftUUID);
    }
}
