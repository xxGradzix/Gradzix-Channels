package me.xxgradzix.channels.objects;

import me.xxgradzix.channels.Channels;
import me.xxgradzix.channels.PlayerInventoryEntityManager;
import me.xxgradzix.channels.entities.PlayerInventoryEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InventorySyncTask extends BukkitRunnable {

	PlayerInventoryEntityManager playerInventoryEntityManager;

	private Channels channels;
	private long startTime;
	private Player p;
	private boolean inProgress = false;
	private InventorySyncData syncD;
	
	public InventorySyncTask(Channels channels, long start, Player player, InventorySyncData syncData, PlayerInventoryEntityManager playerInventoryEntityManager) {
		this.channels = channels;
		this.startTime = start;
		this.p = player;
		this.syncD = syncData;

		this.playerInventoryEntityManager = playerInventoryEntityManager;
	}

	@Override
	public void run() {
		if (inProgress == false) {

			if (p != null) {
				if (p.isOnline() == true) {
					inProgress = true;
					PlayerInventoryEntity playerInventoryEntity = playerInventoryEntityManager.getPlayerInventoryEntityById(p.getUniqueId());

					DatabaseInventoryData data = new DatabaseInventoryData(playerInventoryEntity.getInventory(), playerInventoryEntity.getArmor(), playerInventoryEntity.getEnderChest(), playerInventoryEntity.isSynCompleete(), playerInventoryEntity.getLastSeen());

					if (data.getSyncStatus()) {
						channels.getInventoryDataHandler().setPlayerData(p, data, syncD, true);
						inProgress = false;
						this.cancel();
					} else if (System.currentTimeMillis() - Long.parseLong(data.getLastSeen()) >= 600 * 1000) {
						channels.getInventoryDataHandler().setPlayerData(p, data, syncD, true);
						inProgress = false;
						this.cancel();
					} else if (System.currentTimeMillis() - startTime >= 22 * 1000) {
						channels.getInventoryDataHandler().setPlayerData(p, data, syncD, true);
						inProgress = false;
						this.cancel();
					}
					inProgress = false;
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
