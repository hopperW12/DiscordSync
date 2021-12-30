package cz.hopperw12.discordsync.listeners;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.discord.Bot;
import cz.hopperw12.discordsync.events.UserUnregisterEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UserUnregisterListener implements Listener {

    @EventHandler
    public void onUnregister(UserUnregisterEvent event) {
        DiscordSync main = DiscordSync.getInstance();
        Bot bot = main.bot;

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

        try {
            guild.removeRoleFromMember(member, role).queue();
            member.modifyNickname(null).queue();
        } catch (HierarchyException e) {
            main.getLogger().warning(e.getMessage());
        }

        member.getUser().openPrivateChannel().queue(channel ->
            channel.sendMessage("Byl jsi odebrán ze serveru z důvodu neaktivity").queue()
        );
    }
}
