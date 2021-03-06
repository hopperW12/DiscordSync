package cz.hopperw12.discordsync.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event) {
        if (ListenerService.shouldCancelEvent(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent event) {
        if (ListenerService.shouldCancelEvent(event.getPlayer()))
            event.setCancelled(true);
    }

}
