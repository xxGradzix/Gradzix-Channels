package me.xxgradzix.channels.objects;

import org.bukkit.inventory.ItemStack;

public class InventorySyncData {
	
	private ItemStack[] backupInv;
	private ItemStack[] backupAr;
	private ItemStack[] backupEnderChest;
	private Boolean syncComplete;
	
	public InventorySyncData() {
		this.backupInv = null;
		this.backupAr = null;
		this.backupEnderChest = null;
		this.syncComplete = false;
	}
	
	public void setSyncStatus(boolean syncStatus) {
		syncComplete = syncStatus;
	}
	
	public Boolean getSyncStatus() {
		return syncComplete;
	}
	
	public ItemStack[] getBackupArmor() {
		return backupAr;
	}
	
	public ItemStack[] getBackupInventory() {
		return backupInv;
	}
	public ItemStack[] getBackupEnderChest() {
		return backupEnderChest;
	}
	
	public void setBackupInventory(ItemStack[] backupInventory) {
		backupInv = backupInventory;
	}
	
	public void setBackupArmor(ItemStack[] backupArmor) {
		backupAr = backupArmor;
	}
	public void setBackupEnderChest(ItemStack[] backupEnderChest) {
		this.backupEnderChest = backupEnderChest;
	}

}