package cz.hopperw12.discordsync.commads;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.requests.RequestManager;
import cz.hopperw12.discordsync.requests.Token;
import cz.hopperw12.discordsync.user.UserManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

        if (userManager.isRegistered(player)) {
            player.sendMessage(org.bukkit.ChatColor.RED + "Tvůj účet je již propojen!");
            return false;
        }

        if (userManager.isUnrestricted(player)) {
            player.sendMessage(org.bukkit.ChatColor.GREEN + "Ty propojovat účty nemusíš :-)");
            return false;
        }

        Token token = requestManager.getRequest(player);

        if (token == null || token.hasExpired()) {
            requestManager.clearExpired();
            token = requestManager.addRequest(player);
        }

        TextComponent msg = new TextComponent("\n\n\nPoužíj tento kód: ");
        msg.setBold(true);
        msg.setColor(ChatColor.DARK_GRAY);

        TextComponent tokenMsg = new TextComponent(token.toString());
        tokenMsg.setBold(true);
        tokenMsg.setItalic(true);
        tokenMsg.setUnderlined(true);
        tokenMsg.setColor(ChatColor.GREEN);
        tokenMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Klikni pro zkopírování do schránky").create()));
        tokenMsg.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, token.toString()));

        msg.addExtra(tokenMsg);
        msg.addExtra("\n\n\n");

        player.spigot().sendMessage(msg);

        return false;
    }
}
