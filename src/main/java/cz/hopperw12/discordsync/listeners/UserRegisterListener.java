package cz.hopperw12.discordsync.listeners;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.discord.Bot;
import cz.hopperw12.discordsync.events.UserRegisterEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class UserRegisterListener implements Listener {

    @EventHandler
    public void onRegister(UserRegisterEvent event) {

        DiscordSync main = DiscordSync.getInstance();
        long guildID = main.getConfig().getLong("discord.guildID");
        long roleID = main.getConfig().getLong("discord.roleID");
        Bot bot = main.bot;

        var registerUser = event.getUser();
        Guild guild = bot.jda.getGuildById(guildID);

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

        member.modifyNickname(registerUser.getPlayerName()).queue();
    }

}
