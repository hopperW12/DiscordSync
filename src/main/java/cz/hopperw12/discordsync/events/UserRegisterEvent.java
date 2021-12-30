package cz.hopperw12.discordsync.events;

import cz.hopperw12.discordsync.user.RegisteredUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserRegisterEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RegisteredUser user;

    public UserRegisterEvent(RegisteredUser user) {
        this.user = user;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
