package cz.hopperw12.discordsync.discord.events;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.discord.Bot;
import cz.hopperw12.discordsync.requests.RequestManager;
import cz.hopperw12.discordsync.requests.Token;
import cz.hopperw12.discordsync.user.RegisteredUser;
import cz.hopperw12.discordsync.user.UserManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PrivateMessageReceived extends ListenerAdapter {

    private JDA jda;

    public PrivateMessageReceived(Bot bot) {
        this.jda = bot.jda;
    }

    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        DiscordSync main = DiscordSync.getInstance();
        Bukkit.getScheduler().runTask(main, () -> {
            UserManager userManager = main.userManager;
            RequestManager requestManager = main.requestManager;

            String message = event.getMessage().getContentRaw();
            User user = event.getAuthor();
            Token token = new Token(message);
            UUID playerUUID = requestManager.getRequest(token);

            //Neplatny token
            if (playerUUID == null) {
                user.openPrivateChannel().queue(channel -> {
                    channel.sendMessage("Tento token neexistuje!").queue();
                });
                return;
            }

            // Prošlý token
            token = requestManager.getRequest(playerUUID);
            if (token.hasExpired()) {
                user.openPrivateChannel().queue(channel -> {
                    channel.sendMessage("Tento token expiroval!").queue();
                });
                return;
            }

            //Platny token
            RegisteredUser registeredUser = new RegisteredUser(playerUUID, user.getIdLong());
            registeredUser.setLastOnline(System.currentTimeMillis());

            requestManager.removeRequest(playerUUID);
            userManager.registerUser(registeredUser);
        });
    }
}
