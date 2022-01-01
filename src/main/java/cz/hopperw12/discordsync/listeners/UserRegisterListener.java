package cz.hopperw12.discordsync.listeners;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.discord.Bot;
import cz.hopperw12.discordsync.discord.Messages;
import cz.hopperw12.discordsync.events.UserRegisterEvent;
import cz.hopperw12.discordsync.user.UserManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UserRegisterListener implements Listener {

    @EventHandler
    public void onRegister(UserRegisterEvent event) {

        DiscordSync main = DiscordSync.getInstance();
        UserManager userManager = main.userManager;

        long guildID = main.getConfig().getLong("discord.guildID");
        long roleID = main.getConfig().getLong("discord.roleID");
        Bot bot = main.bot;

        var registerUser = event.getUser();
        Guild guild = bot.jda.getGuildById(guildID);

        OfflinePlayer offlinePlayer = registerUser.getOfflinePlayer();
        Player player = offlinePlayer.getPlayer();

        if (userManager.isUnrestricted(offlinePlayer)) {
            main.getLogger().severe("Unrestricted player '" + offlinePlayer.getName() + "' has linked accounts (this should not happen)");
            return;
        }

        if (guild == null) {
            main.getLogger().warning("Defined guildID doesn't match any guilds");
            return;
        }

        Role role = guild.getRoleById(roleID);

        if (role == null) {
            main.getLogger().warning("Defined roleID doesn't match any roles");
            return;
        }

        guild.addRoleToMember(registerUser.getDiscordUUID(), role).queue();

        Member member = guild.getMemberById(registerUser.getDiscordUUID());
        if (member == null)
            return;

        User user = member.getUser();
        String discordAccountName = user.getName() + "#" + user.getDiscriminator();

        try {
            member.modifyNickname(registerUser.getPlayerName()).queue();
        } catch (HierarchyException e) {
            main.getLogger().warning(e.getMessage() + " (" + discordAccountName + ")");
        }

        if (player != null)
            player.sendMessage("Účet byl úspěšně propojen s tvým Discord účtem " + discordAccountName);

        user.openPrivateChannel().queue(channel ->
            channel.sendMessage(Messages.getPlayerRegisteredMessage(offlinePlayer.getName())).queue()
        );
    }
}
