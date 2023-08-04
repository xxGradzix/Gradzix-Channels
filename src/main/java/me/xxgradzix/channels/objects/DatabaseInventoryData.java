package me.xxgradzix.channels;

import org.bukkit.inventory.ItemStack;

public class DatabaseInventoryData {

    private ItemStack[] inventory;
    private ItemStack[] armor;
    private boolean syncComplete;
//    private String lastSee;

    public DatabaseInventoryData(ItemStack[] inventory, ItemStack[] armor, boolean syncStatus) {
        this.inventory = inventory;
        this.armor = armor;
        this.syncComplete = syncStatus;
//        this.lastSee = lastSeen;
    }

//    public String getLastSeen() {
//        return lastSee;
//    }

    public boolean getSyncStatus() {
        return syncComplete;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

}
