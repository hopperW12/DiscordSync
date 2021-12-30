package cz.hopperw12.discordsync.discord;

import cz.hopperw12.discordsync.discord.events.PrivateMessageReceived;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {

    public JDA jda;

    public Bot(String token) {

        try {
            this.jda = JDABuilder
                    .createLight(token)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES)
                    .addEventListeners(new PrivateMessageReceived(this))
                    .build();

            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
