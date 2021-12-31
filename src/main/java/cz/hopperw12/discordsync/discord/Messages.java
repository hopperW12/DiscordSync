package cz.hopperw12.discordsync.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Date;

public class Messages {
    public static MessageEmbed getTokenExpiredMessage() {
        return new EmbedBuilder()
            .setTitle("**Tento token expiroval!**")
            .setDescription("""
                Pro vygenerování nového tokenu napiš do chatu hry
                ```/link```"""
            )
            .setColor(Color.RED)
            .setFooter("JménoServeru Sync")
            .setTimestamp(new Date().toInstant())
            .build();
    }

    public static MessageEmbed getAccountAlreadyLinkedMessage(String oldAccount) {
        return new EmbedBuilder()
            .setTitle("**Tento účet je již propojen s účtem jiným!**")
            .setDescription("""
                Přihlašte se do účtu ***""" + oldAccount + """
                *** a napište do chatu hry
                ```/unlink```
                nebo kontaktujte člena Admin-Teamu."""
            )
            .setColor(Color.RED)
            .setFooter("JménoServeru Sync")
            .setTimestamp(new Date().toInstant())
            .build();
    }

    public static MessageEmbed getTokenNotExistsMessage() {
        return new EmbedBuilder()
            .setTitle("**Tento token neexistuje!**")
            .setDescription("""
                Pro vygenerování tokenu napiš do chatu hry
                ```/link```"""
            )
            .setColor(Color.RED)
            .setFooter("JménoServeru Sync")
            .setTimestamp(new Date().toInstant())
            .build();
    }

    public static MessageEmbed getPlayerUnregisteredMessage() {
        return new EmbedBuilder()
            .setTitle("**Byl jsi odebrán ze serveru!**")
            .setDescription("""
                Tvůj účet byl odpojen manuálně, anebo z důvodu neaktivity.
                Pro znovu-připojení kontaktuj člena Admin-Teamu.
                """
            )
            .setColor(Color.RED)
            .setFooter("JménoServeru Sync")
            .setTimestamp(new Date().toInstant())
            .build();
    }

    public static MessageEmbed getPlayerRegisteredMessage(String playerName) {
        return new EmbedBuilder()
            .setTitle("**Úspěšná registrace!**")
            .setDescription("""
                Tvůj účet byl úspěšně propojen s Minecraft účtem ***
                """ + playerName + "***"
            )
            .setColor(Color.GREEN)
            .setFooter("JménoServeru Sync")
            .setTimestamp(new Date().toInstant())
            .build();
    }
}
