package cz.hopperw12.discordsync.requests;

import cz.hopperw12.discordsync.DiscordSync;

import java.util.Objects;
import java.util.Random;

public class Token {
    private final String value;
    private final long ttl;

    public Token(String value) {
        DiscordSync main = DiscordSync.getInstance();

        long expiration = main.getConfig().getLong("timings.token.expiration");
        expiration *= 60 * 1000;

        this.value = value;
        this.ttl = System.currentTimeMillis() + expiration;
    }

    public Token(String value, long ttl) {
        this.value = value;
        this.ttl = ttl;
    }

    public long getTTL() {
        return ttl;
    }

    public boolean hasExpired() {
        return ttl < System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return value;
    }

    public static Token generate(int length) {
        final String alphabet = "AaBbCcDdEeF?.fGgHhIiJkKlLlMm:NnOoPpQqRrSsTtUuVvW!wXxYyZz0123456789";

        String key = "";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        key = sb.toString();
        return new Token(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
