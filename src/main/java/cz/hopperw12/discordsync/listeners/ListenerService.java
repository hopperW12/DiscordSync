package cz.hopperw12.discordsync.listeners;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.user.UserManager;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;

public class ListenerService {

    public static boolean shouldCancelEvent(PlayerEvent event) {
        return shouldCancelEvent(event.getPlayer());
    }

    public static boolean shouldCancelEvent(EntityEvent event) {
        return shouldCancelEvent(event.getEntity());
    }

    public static boolean shouldCancelEvent(Entity entity) {
        if (!(entity instanceof Player player))
            return false;
        return shouldCancelEvent(player);
    }

    public static boolean shouldCancelEvent(Player player) {

        DiscordSync main = DiscordSync.getInstance();
        UserManager userManager = main.userManager;
        AuthMeApi authMeApi = AuthMeApi.getInstance();

        return !userManager.isRegistered(player)
            && !userManager.isUnrestricted(player)
            && authMeApi.isAuthenticated(player);

    }

}
