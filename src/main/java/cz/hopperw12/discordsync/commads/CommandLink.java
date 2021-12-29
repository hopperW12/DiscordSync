package cz.hopperw12.discordsync.commads;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.requests.RequestManager;
import cz.hopperw12.discordsync.requests.Token;
import cz.hopperw12.discordsync.user.UserManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLink implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        DiscordSync main = DiscordSync.getInstance();
        UserManager userManager = main.userManager;
        RequestManager requestManager = main.requestManager;

        if (!(sender instanceof Player))
            return false;

        Player player = ((Player) sender);

        if (userManager.isRegistered(player))
            player.sendMessage("Už jsi zaregistrovaný!");

        else if (requestManager.hasRequest(player)) {
            Token token = requestManager.getRequest(player);
            player.sendMessage("Použij tento token: " + token);
        }

        else {
            Token token = requestManager.addRequest(player);
            player.sendMessage("Použij tento token: " + token);
        }

        return false;
    }
}
