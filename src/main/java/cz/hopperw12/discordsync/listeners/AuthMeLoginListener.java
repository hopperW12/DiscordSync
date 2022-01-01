package cz.hopperw12.discordsync.listeners;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.user.UserManager;
import fr.xephi.authme.events.LoginEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AuthMeLoginListener implements Listener {

    @EventHandler
    public void onLogin(LoginEvent event) {

        Player player = event.getPlayer();
        DiscordSync main = DiscordSync.getInstance();
        UserManager userManager = main.userManager;

        if (!userManager.isRegistered(player) && !userManager.isUnrestricted(player)) {
            TextComponent msg = new TextComponent("\nPřed pokračováním propoj účet s discordem! (/link)\n");
            msg.setBold(true);
            msg.setColor(ChatColor.RED);

            player.spigot().sendMessage(msg);
        }
    }

}
