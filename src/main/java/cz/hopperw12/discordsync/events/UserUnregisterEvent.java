package cz.hopperw12.discordsync.events;

import cz.hopperw12.discordsync.user.RegisteredUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserUnregisterEvent extends Event {
    public enum Reason {
        INACTIVITY,
        MANUAL
    }

    private static final HandlerList HANDLERS = new HandlerList();
    private final RegisteredUser user;
    private final Reason reason;

    public UserUnregisterEvent(RegisteredUser user, Reason reason) {
        this.user = user;
        this.reason = reason;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public Reason getReason() {
        return reason;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
