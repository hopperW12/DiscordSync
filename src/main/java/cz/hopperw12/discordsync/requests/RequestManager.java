package cz.hopperw12.discordsync.requests;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RequestManager {
    private Map<Player, Token> requests = new HashMap<>();

    public Token addRequest(Player player) {
        Token token;

        do {
            token = Token.generate(5);
        } while (requests.containsValue(token));

        requests.put(player, token);
        return token;
    }

    public Token getRequest(Player player) {
        return requests.get(player);
    }

    public void removeRequest(Player player) {
        requests.remove(player);
    }

    public boolean hasRequest(Player player) {
        return requests.containsKey(player);
    }
}
