package me.xxgradzix.channels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BackgroundTask {
	
	private Channels channels;
	
	public BackgroundTask(Channels channels) {
		this.channels = channels;
		runTask();
	}
	
	private void runTask() {
		if (true) {

			Bukkit.getScheduler().runTaskTimerAsynchronously(channels, new Runnable() {

				@Override
				public void run() {
					runSaveData();
				}
				
			}, 10 * 60 * 20L, 10 * 60 * 20L);
		}
	}
	
	private void runSaveData() {
		if (true) {
			if (Bukkit.getOnlinePlayers().isEmpty() == false) {
				List<Player> onlinePlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());

				for (Player p : onlinePlayers) {
					if (p.isOnline() == true) {
						channels.getInventoryDataHandler().onDataSaveFunction(p, false, false, null, null, null, null);
					}
				}

				onlinePlayers.clear();
			}
		}
	}
	
	public void onShutDownDataSave() {
		List<Player> onlinePlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
		
		for (Player p : onlinePlayers) {
			if (p.isOnline() == true) {
				channels.getInventoryDataHandler().onDataSaveFunction(p, false, true, null, null, null, null);
			}
		}
	}

}
