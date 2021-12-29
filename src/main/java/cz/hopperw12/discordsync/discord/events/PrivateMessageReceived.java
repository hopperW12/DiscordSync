package cz.hopperw12.discordsync.discord.events;

import cz.hopperw12.discordsync.discord.Bot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrivateMessageReceived extends ListenerAdapter {

    private JDA jda;

    public PrivateMessageReceived(Bot bot) {
        this.jda = bot.jda;
    }

    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        String message = event.getMessage().getContentRaw();
        User user = event.getAuthor();


    }



}
