package me.xxgradzix.channels.objects;

import me.xxgradzix.channels.Channels;
import me.xxgradzix.channels.database.managers.PlayerInventoryEntityManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SyncCompleteTask extends BukkitRunnable {

	PlayerInventoryEntityManager playerInventoryEntityManager;
	private Channels channels;
	private long startTime;
	private Player p;
	private boolean inProgress = false;
	
	public SyncCompleteTask(Channels channels, long start, Player player, PlayerInventoryEntityManager playerInventoryEntityManager) {
		this.channels = channels;
		this.startTime = start;
		this.p = player;
		this.playerInventoryEntityManager = playerInventoryEntityManager;
	}

	@Override
	public void run() {
		if (inProgress == false) {
			if (p != null) {
				if (p.isOnline() == true) {
					inProgress = true;
					if (channels.getInventoryDataHandler().isSyncComplete(p)) {
						this.cancel();
					} else {
						if (System.currentTimeMillis() - startTime >= 20 * 1000) {
							//Set sync to true in database to force sync data after 20 sec
							playerInventoryEntityManager.setPlayerInventoryEntitySyncStatus(p, true);
						} else if (System.currentTimeMillis() - startTime >= 40 * 1000) {
							//Stop task after 40 sec
							this.cancel();
						}
						
					}
				} else {
					//inProgress = false;
					this.cancel();
				}
			} else {
				//inProgress = false;
				this.cancel();
			}
		}
	}
	
	

}
