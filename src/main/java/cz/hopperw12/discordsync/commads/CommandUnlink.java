package cz.hopperw12.discordsync.commads;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.events.UserUnregisterEvent;
import cz.hopperw12.discordsync.user.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandUnlink implements CommandExecutor {

    private List<String> players =new ArrayList<String>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        DiscordSync main = DiscordSync.getInstance();
        UserManager userManager = main.userManager;

        if (sender instanceof Player player) {
            if (args.length != 1) {
                String playerName = player.getName();

                if (!userManager.isRegistered(playerName)) {
                    player.sendMessage(ChatColor.RED + "Tvůj účet není propojený s Discordem.");
                    return true;
                }

                if (!players.contains(playerName)) {
                    players.add(playerName);
                    player.sendMessage(ChatColor.RED + "Pro potvrzení napiš " + ChatColor.GREEN + "/unlink");
                    return true;
                }

                players.remove(playerName);
                userManager.unregisterUser(playerName, UserUnregisterEvent.Reason.MANUAL);
                return true;
            }

            if (!player.hasPermission("discordsync.admin")) {
                player.sendMessage(ChatColor.RED + "Na tuto akci nemáš dostatečné oprávnění!");
            }

            String playerName = args[0];

            if (!userManager.isRegistered(playerName)) {
                player.sendMessage(ChatColor.RED + "Tento hráč nemá propojený účet s Discordem.");
                return true;
            }

            userManager.unregisterUser(playerName, UserUnregisterEvent.Reason.MANUAL);
            player.sendMessage(ChatColor.GREEN + "Hráč byl odpojen.");
            return true;
        }
        else if (sender instanceof ConsoleCommandSender console) {
            if (args.length != 1)
                return false;

            String playerName = args[0];

            if (!userManager.isRegistered(playerName)) {
                console.sendMessage("Player doesn't have a linked discord account.");
                return true;
            }

            userManager.unregisterUser(playerName, UserUnregisterEvent.Reason.MANUAL);
            console.sendMessage("Player unlinked");
            return true;
        }

        return true;
    }
}
