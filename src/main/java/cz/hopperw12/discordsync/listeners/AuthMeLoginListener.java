package cz.hopperw12.discordsync.listeners;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.user.UserManager;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AuthMeLoginListener implements Listener {

    @EventHandler
    public void onLogin(LoginEvent event) {

        Player player = event.getPlayer();
        DiscordSync main = DiscordSync.getInstance();
        UserManager userManager = main.userManager;

        if (!userManager.isRegistered(player) && !userManager.isUnrestricted(player))
            player.sendMessage(ChatColor.DARK_RED + "Před pokračování propoj účet s discordem! (/link)");
    }

}
