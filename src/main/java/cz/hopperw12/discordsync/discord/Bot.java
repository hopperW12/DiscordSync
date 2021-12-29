package cz.hopperw12.discordsync.discord;

import cz.hopperw12.discordsync.discord.events.PrivateMessageReceived;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    public JDA jda;

    public Bot(String token) {

        try {
            this.jda = JDABuilder
                    .createLight(token)
                    .addEventListeners(new PrivateMessageReceived(this))
                    .build();

            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
