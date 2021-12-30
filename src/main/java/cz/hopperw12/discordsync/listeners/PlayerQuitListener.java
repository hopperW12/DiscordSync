package cz.hopperw12.discordsync.listeners;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.user.RegisteredUser;
import cz.hopperw12.discordsync.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        DiscordSync main = DiscordSync.getInstance();
        UserManager userManager = main.userManager;
        Player player = event.getPlayer();

        if (!userManager.isRegistered(player))
            return;

        RegisteredUser registeredUser = userManager.get(player);
        registeredUser.setLastOnline(System.currentTimeMillis());

    }

}
