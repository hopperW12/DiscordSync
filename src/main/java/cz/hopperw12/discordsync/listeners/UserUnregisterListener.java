package cz.hopperw12.discordsync.listeners;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.discord.Bot;
import cz.hopperw12.discordsync.discord.Messages;
import cz.hopperw12.discordsync.events.UserUnregisterEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UserUnregisterListener implements Listener {

    @EventHandler
    public void onUnregister(UserUnregisterEvent event) {
        DiscordSync main = DiscordSync.getInstance();
        Bot bot = main.bot;

        OfflinePlayer offlinePlayer = event.getUser().getOfflinePlayer();
        Player player = offlinePlayer.getPlayer();

        if (event.getReason() == UserUnregisterEvent.Reason.UNRESTRICTED)
            return;

        if (event.getReason() == UserUnregisterEvent.Reason.INACTIVITY)
            main.getLogger().info("Unlinking player '" + offlinePlayer.getName() + "' due to inactivity");

        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            String.format("easywl remove %s", event.getUser().getPlayerName())
        );

        long guildID = main.getConfig().getLong("discord.guildID");
        long roleID = main.getConfig().getLong("discord.roleID");

        Guild guild = bot.jda.getGuildById(guildID);

        if (guild == null) {
            main.getLogger().warning("Defined guildID doesn't match any guilds");
            return;
        }

        Member member = guild.getMemberById(event.getUser().getDiscordUUID());

        if (member == null) {
            return;
        }

        Role role = guild.getRoleById(roleID);

        if (role == null) {
            main.getLogger().warning("Defined roleID doesn't match any roles");
            return;
        }

        User user = member.getUser();
        String discordAccountName = user.getName() + "#" + user.getDiscriminator();

        try {
            guild.removeRoleFromMember(member, role).queue();
            member.modifyNickname(null).queue();
        } catch (HierarchyException e) {
            main.getLogger().warning(e.getMessage() + " (" + discordAccountName + ")");
        }

        if (player != null)
            player.kickPlayer("Tvůj účet byl úspěšně odpojen. Pro znovu-získání přístupu napiš členovi Admin-Teamu.");

        member.getUser().openPrivateChannel().queue(channel ->
            channel.sendMessage(Messages.getPlayerUnregisteredMessage(event.getReason())).queue()
        );
    }
}
