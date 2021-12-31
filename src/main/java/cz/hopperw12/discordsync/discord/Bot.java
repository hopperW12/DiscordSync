package cz.hopperw12.discordsync.discord;

import cz.hopperw12.discordsync.DiscordSync;
import cz.hopperw12.discordsync.discord.events.PrivateMessageReceived;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Bot {

    public JDA jda;

    public Bot(String token) {
        DiscordSync main = DiscordSync.getInstance();

        try {
            this.jda = JDABuilder
                    .createLight(token)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES)
                    .addEventListeners(new PrivateMessageReceived(this))
                    .build();

            jda.awaitReady();
        } catch (LoginException e) {
            main.getLogger().severe("Invalid discord login token!");
            main.getServer().getPluginManager().disablePlugin(main);
        } catch (Exception e) {
            e.printStackTrace();
            main.getServer().getPluginManager().disablePlugin(main);
        }
    }

}
