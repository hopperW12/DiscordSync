package cz.hopperw12.discordsync.tabcompleter;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.user.RegisteredUser;
import cz.hopperw12.discordsync.user.UserManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class UnlinkTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player player))
            return null;

        if (!player.hasPermission("discordsync.admin"))
            return null;

        DiscordSync main = DiscordSync.getInstance();
        UserManager userManager = main.userManager;

        return userManager.getAll().stream()
                .map(RegisteredUser::getPlayerName)
                .collect(Collectors.toList());
    }
}
