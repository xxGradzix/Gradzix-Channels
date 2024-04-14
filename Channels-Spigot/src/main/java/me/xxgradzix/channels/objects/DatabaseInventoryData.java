package me.xxgradzix.channels.objects;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class DatabaseInventoryData {

    private ItemStack[] inventory;
    private ItemStack[] armor;
    private ItemStack[] enderChest;
    private Location location;
    private boolean syncComplete;
    private String lastSeen;

    public DatabaseInventoryData(ItemStack[] inventory, ItemStack[] armor, ItemStack[] enderChest, Location location, boolean syncStatus, String lastSeen) {
        this.inventory = inventory;
        this.armor = armor;
        this.enderChest = enderChest;
        this.location = location;
        this.syncComplete = syncStatus;
        this.lastSeen = lastSeen;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public boolean getSyncStatus() {
        return syncComplete;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }
    public ItemStack[] getEnderChest() {
        return enderChest;
    }

    public Location getLocation() {
        return location;
    }
}
