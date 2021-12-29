package cz.hopperw12.discordsync.user;

import java.util.UUID;

public class RegisteredUser {
    private UUID minecraftUUID;
    private long discordUUID;

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
}
