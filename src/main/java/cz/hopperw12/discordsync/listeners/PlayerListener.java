package cz.hopperw12.discordsync.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (ListenerService.shouldCancelEvent(event))
            event.setCancelled(true);
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        if (ListenerService.shouldCancelEvent(event.getWhoClicked()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (ListenerService.shouldCancelEvent(event))
            event.setCancelled(true);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (ListenerService.shouldCancelEvent(event))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {

        String command = event.getMessage();

        if (command.equalsIgnoreCase("/link"))
            return;

        if (ListenerService.shouldCancelEvent(event))
            event.setCancelled(true);
    }





}
