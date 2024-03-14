package me.xxgradzix.channels;

import me.xxgradzix.channels.entities.PlayerInventoryEntity;
import me.xxgradzix.channels.objects.DatabaseInventoryData;
import me.xxgradzix.channels.objects.InventorySyncData;
import me.xxgradzix.channels.objects.InventorySyncTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class InventoryDataHandler {

    private final PlayerInventoryEntityManager playerInventoryEntityManager;

    private Channels channels;
    private Set<Player> playersInSync = new HashSet<>();
    private Set<Player> playersDisconnectSave = new HashSet<>();

    public InventoryDataHandler(Channels channels, PlayerInventoryEntityManager playerInventoryEntityManager) {
        this.channels = channels;
        this.playerInventoryEntityManager = playerInventoryEntityManager;
    }

    public boolean isSyncComplete(Player player) {
        if(playersInSync.contains(player)) {
            return true;
        } else {
            return false;
        }
    }
    public void dataCleanUp(Player player) {
        playersInSync.remove(player);
        playersDisconnectSave.remove(player);
    }

    public void setPlayerData(final Player player, DatabaseInventoryData data, InventorySyncData syncData, boolean cancelTask) {

        if (playersInSync.contains(player)) {
            System.out.println("Player is in sync");
            return;
        }
        setInventory(player, data, syncData);
        Bukkit.getScheduler().runTaskLaterAsynchronously(channels, new Runnable() {

            @Override
            public void run() {
                playersInSync.add(player);
            }

        }, 2L);
        data = null;
    }

    public void onDataSaveFunction(Player p, Boolean dataCleanUp, boolean syncStatus, ItemStack[] inventoryDisconnect, ItemStack[] armorDisconnect, ItemStack[] enderChestDisconnect, Location locationDisconnect) {
        if (playersDisconnectSave.contains(p) == true) {
            return;
        }
        if (dataCleanUp) {
            playersDisconnectSave.add(p);
        }
        boolean isPlayerInSync = playersInSync.contains(p);
        if (isPlayerInSync) {
            ItemStack[] inv = null;
            ItemStack[] armor = null;
            ItemStack[] enderChest = null;
            Location location = null;

            try {
                if (inventoryDisconnect != null) {
                    inv = inventoryDisconnect;
                } else {
                    inv = p.getInventory().getContents();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
//                if (inventoryDisconnect != null) {
                if (armorDisconnect != null) {
                    armor = armorDisconnect;
                } else {
                    armor = p.getInventory().getArmorContents();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (enderChestDisconnect != null) {
                    enderChest = enderChestDisconnect;
                } else {
                    enderChest = p.getEnderChest().getContents();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (locationDisconnect != null) {
                    location = locationDisconnect;
                } else {
                    location = p.getLocation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            channels.getPlayerInventoryEntityManager().createOrUpdatePlayerInventoryEntity(new PlayerInventoryEntity(p.getUniqueId(), p.getName(), inv, armor, enderChest, location, syncStatus, String.valueOf(System.currentTimeMillis())));
        }
        if (dataCleanUp) {
            dataCleanUp(p);
        }
    }

    public void onJoinFunction(final Player p) {
        if (channels.isDisabling) {
            System.out.printf("Plugin is disabling");
            return;
        }

            if (!playersInSync.contains(p)) {

                if (channels.getPlayerInventoryEntityManager().getPlayerInventoryEntityById(p.getUniqueId()) != null) {
                    final InventorySyncData syncData = new InventorySyncData();
                    backupAndReset(p, syncData);

                    PlayerInventoryEntity playerInventoryEntity =channels.getPlayerInventoryEntityManager().getPlayerInventoryEntityById(p.getUniqueId());

//                    DatabaseInventoryData data = pd.getInvMysqlInterface().getData(p);
                    DatabaseInventoryData data = new DatabaseInventoryData(playerInventoryEntity.getInventory(), playerInventoryEntity.getArmor(), playerInventoryEntity.getEnderChest(), playerInventoryEntity.getLocation(), playerInventoryEntity.isSynCompleete(), playerInventoryEntity.getLastSeen());

                    if (data.getSyncStatus()) {
                        setPlayerData(p, data, syncData, false);

                    } else {
                        new InventorySyncTask(channels, System.currentTimeMillis(), p, syncData, playerInventoryEntityManager).runTaskTimerAsynchronously(channels, 10L, 10L);
                    }
                } else {
                    playersInSync.add(p);
                    /**
                     * changed sync status to true
                     */
                    onDataSaveFunction(p, true, false, null, null, null, null);
                }

        }
    }
    private void backupAndReset(Player p, InventorySyncData syncData) {
        syncData.setBackupInventory(p.getInventory().getContents());
        syncData.setBackupArmor(p.getInventory().getArmorContents());
        syncData.setBackupEnderChest(p.getEnderChest().getContents());
        syncData.setBackupLocation(p.getLocation());
        p.setItemOnCursor(null);
        p.getInventory().clear();
        p.updateInventory();
//        if (channels.getConfigHandler().getBoolean("General.syncArmorEnabled") == true) {
            syncData.setBackupArmor(p.getInventory().getArmorContents());
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);
            p.updateInventory();
//        }
    }

    public ItemStack[] getInventory(Player p) {
        return p.getInventory().getContents();
    }

    public ItemStack[] getArmor(Player p) {
            return p.getInventory().getArmorContents();
    }
    public ItemStack[] getEnderChest(Player p) {
        return p.getEnderChest().getContents();
    }
    public Location getLocation(Player p) {
        return p.getLocation();
    }
    private void setInventory(final Player p, DatabaseInventoryData data, InventorySyncData syncData) {
        if (data.getInventory() != null) {
            try {
                p.getInventory().setContents(data.getInventory());
                // nowe
                p.getInventory().setArmorContents(data.getArmor());
                p.getEnderChest().setContents(data.getEnderChest());
                p.teleport(data.getLocation());
            } catch (Exception e) {
                e.printStackTrace();
                if (syncData.getBackupInventory() != null) {
                    p.getInventory().setContents(syncData.getBackupInventory());
                    p.getInventory().setArmorContents(syncData.getBackupArmor());
                    p.getEnderChest().setContents(syncData.getBackupEnderChest());
                    p.teleport(syncData.getBackupLocation());
                } else {

                }
            }
        } else {
            p.getInventory().setContents(syncData.getBackupInventory());
            p.getInventory().setArmorContents(syncData.getBackupArmor());
            p.getEnderChest().setContents(syncData.getBackupEnderChest());
            p.teleport(syncData.getBackupLocation());
        }
        p.updateInventory();
    }
    private void setInventoryNew(final Player p, DatabaseInventoryData data, InventorySyncData syncData) {
        if (data.getInventory() != null) {
            try {
                p.getInventory().setContents(data.getInventory());
                p.getInventory().setArmorContents(syncData.getBackupArmor());

            } catch (Exception e) {
                e.printStackTrace();
                if (syncData.getBackupInventory() != null) {
                    p.getInventory().setContents(syncData.getBackupInventory());
                }
            }
        } else {

            p.getInventory().setContents(syncData.getBackupInventory());
        }
        p.updateInventory();
    }

    private void setArmor(final Player p, DatabaseInventoryData data, InventorySyncData syncData) {
        if (data.getArmor() != null) {
            try {
                p.getInventory().setArmorContents(data.getArmor());
            } catch (Exception e) {
                e.printStackTrace();
                p.getInventory().setArmorContents(syncData.getBackupArmor());
            }
        } else {
            p.getInventory().setArmorContents(syncData.getBackupArmor());
        }
        p.updateInventory();
    }
    private void setEnderChest(final Player p, DatabaseInventoryData data, InventorySyncData syncData) {
        if (data.getEnderChest() != null) {
            try {
                p.getEnderChest().setContents(data.getEnderChest());
            } catch (Exception e) {
                e.printStackTrace();
                p.getEnderChest().setContents(syncData.getBackupEnderChest());
            }
        } else {
            p.getEnderChest().setContents(syncData.getBackupEnderChest());
        }
        p.updateInventory();
    }


}
