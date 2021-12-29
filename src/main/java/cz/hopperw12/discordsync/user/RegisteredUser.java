package cz.hopperw12.discordsync.user;

import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class RegisteredUser {
    private UUID minecraftUUID;
    private long discordUUID;
    private long lastOnline;

    public RegisteredUser(Player player, long discordUUID) {
        this(player.getUniqueId(), discordUUID);
    }

    public RegisteredUser(UUID minecraftUUID, long discordUUID) {
        this.minecraftUUID = minecraftUUID;
        this.discordUUID = discordUUID;
    }

    public UUID getMinecraftUUID() {
        return minecraftUUID;
    }

    public long getDiscordUUID() {
        return discordUUID;
    }

    public long getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(long lastOnline) {
        this.lastOnline = lastOnline;
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
