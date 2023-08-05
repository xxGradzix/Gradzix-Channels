package me.xxgradzix.channels.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import me.xxgradzix.channels.persisters.ItemStackArrayPersister;
import me.xxgradzix.channels.persisters.LocationPersister;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@DatabaseTable(tableName = "player_inventories")
public class PlayerInventoryEntity {

    public PlayerInventoryEntity() {
    }

    public PlayerInventoryEntity(UUID uuid, String playerName, ItemStack[] inventory, ItemStack[] armor, ItemStack[] enderChest, Location location, boolean isSynCompleete, String lastSeen) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.inventory = inventory;
        this.armor = armor;
        this.enderChest = enderChest;
        this.location = location;
        this.isSynCompleete = isSynCompleete;
        this.lastSeen = lastSeen;
    }

    @DatabaseField(unique = true, id = true)
    private UUID uuid;

    @DatabaseField
    private String playerName;

//    @DatabaseField(persisterClass = ItemStackArrayPersister.class, columnDefinition = "BLOB")
    @DatabaseField(persisterClass = ItemStackArrayPersister.class, columnDefinition = "LONGBLOB")
    private ItemStack[] inventory;

    @DatabaseField(persisterClass = ItemStackArrayPersister.class, columnDefinition = "LONGBLOB")
    private ItemStack[] armor;


    @DatabaseField(persisterClass = ItemStackArrayPersister.class, columnDefinition = "LONGBLOB")
    private ItemStack[] enderChest;

    @DatabaseField(persisterClass = LocationPersister.class, columnDefinition = "TEXT")
//    @DatabaseField
    private Location location;

    @DatabaseField
    private boolean isSynCompleete;

    @DatabaseField
    private String lastSeen;

    public Location getLocation() {
        return location;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public ItemStack[] getEnderChest() {
        return enderChest;
    }

    public void setEnderChest(ItemStack[] enderChest) {
        this.enderChest = enderChest;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public boolean isSynCompleete() {
        return isSynCompleete;
    }
    public String getLastSeen() {
        return lastSeen;
    }

    public void setSynCompleete(boolean synCompleete) {
        isSynCompleete = synCompleete;
    }
}
