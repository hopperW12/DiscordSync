package cz.hopperw12.discordsync.discord;

import cz.hopperw12.discordsync.events.UserUnregisterEvent;
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

    public static MessageEmbed getPlayerUnregisteredMessage(UserUnregisterEvent.Reason reason) {
        return switch (reason) {
            case INACTIVITY -> new EmbedBuilder()
                .setTitle("**Byl jsi odebrán ze serveru!**")
                .setDescription("""
                    Tvůj účet byl odpojen z důvodu neaktivity.
                    Pro znovu-připojení kontaktuj člena Admin-Teamu.
                    """
                )
                .setColor(Color.RED)
                .setFooter("JménoServeru Sync")
                .setTimestamp(new Date().toInstant())
                .build();

            case MANUAL -> new EmbedBuilder()
                .setTitle("**Byl jsi odebrán ze serveru!**")
                .setDescription("""
                    Tvůj účet byl úspěšně odpojen.
                    Pro znovu-připojení kontaktuj člena Admin-Teamu.
                    """
                )
                .setColor(Color.RED)
                .setFooter("JménoServeru Sync")
                .setTimestamp(new Date().toInstant())
                .build();

            case UNRESTRICTED -> new EmbedBuilder()
                .setTitle("**Chyba aplikace!**")
                .setDescription("""
                    Pokud si dostal tuhle zprávu,
                    tak se něco hodně pokazilo.
                    
                    Kontaktuj někoho z Admin-Teamu
                    a pošli mu screen.
                """
                )
                .setColor(Color.RED)
                .setFooter("JménoServeru Sync")
                .setTimestamp(new Date().toInstant())
                .build();
        };
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
