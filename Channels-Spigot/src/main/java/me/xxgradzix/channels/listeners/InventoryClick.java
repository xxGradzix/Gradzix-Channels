package me.xxgradzix.channels.listeners;

import me.xxgradzix.channels.Channels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
	
	private Channels channels;
	
	public InventoryClick(Channels channels) {
		this.channels = channels;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
//		if (channels.getInventoryDataHandler().isSyncComplete(p) == true) {
		if (channels.getInventoryDataHandler().isSyncComplete(p) == false) {
			event.setCancelled(true);
		}
	}

}
