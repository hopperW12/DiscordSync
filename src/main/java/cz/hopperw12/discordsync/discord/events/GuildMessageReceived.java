package cz.hopperw12.discordsync.discord.events;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.discord.Bot;
import cz.hopperw12.discordsync.user.RegisteredUser;
import cz.hopperw12.discordsync.user.UserManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class GuildMessageReceived extends ListenerAdapter {

    private JDA jda;
    private DiscordSync main;
    private long channelID;

    public GuildMessageReceived(Bot bot) {
        this.jda = bot.jda;
        this.main = DiscordSync.getInstance();
        this.channelID = main.getConfig().getLong("discord.channelID");
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getChannel().getIdLong() != channelID)
            return;

        if (event.getAuthor().isBot())
            return;

        DiscordSync main = DiscordSync.getInstance();
        Bukkit.getScheduler().runTask(main, () -> {
            UserManager userManager = main.userManager;
            User user = event.getAuthor();

            RegisteredUser registeredUser = userManager.get(user.getIdLong());

            if (registeredUser != null) {
                registeredUser.setLastOnline(System.currentTimeMillis());
            }
        });
    }
}
