package cz.hopperw12.discordsync.requests;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestManager {
    private Map<UUID, Token> requests;

    public RequestManager() {
        requests = new HashMap<>();
    }

    public Token addRequest(Player player) {
        return addRequest(player.getUniqueId());
    }

    public Token addRequest(UUID playerUUID) {
        Token token;

        do {
            token = Token.generate(5);
        } while (requests.containsValue(token));

        requests.put(playerUUID, token);
        return token;
    }

    public Token getRequest(Player player) {
        return getRequest(player.getUniqueId());
    }

    public Token getRequest(UUID playerUUID) {
        return requests.get(playerUUID);
    }

    public UUID getRequest(Token token) {
        return requests.entrySet()
                .stream()
                .filter(entry -> token.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);
    }

    public void removeRequest(Player player) {
        removeRequest(player.getUniqueId());
    }

    public void removeRequest(UUID playerUUID) {
        requests.remove(playerUUID);
    }

    public boolean hasRequest(Player player) {
        return hasRequest(player);
    }

    public boolean hasRequest(UUID playerUUID) {
        return requests.containsKey(playerUUID);
    }

    public void clearExpired() {
        requests.entrySet()
                .removeIf(entry -> entry.getValue().getTTL() < System.currentTimeMillis());
    }
}
