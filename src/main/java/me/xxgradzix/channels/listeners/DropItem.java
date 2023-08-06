package me.xxgradzix.channels.listeners;

import me.xxgradzix.channels.Channels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropItem implements Listener {
	
	private Channels channels;
	
	public DropItem(Channels channels) {
		this.channels = channels;
	}
	
	@EventHandler
	public void onItemDrop(final PlayerDropItemEvent event) {
		if (channels.getInventoryDataHandler().isSyncComplete(event.getPlayer()) == false) {
			event.setCancelled(true);
		}
	}

}
