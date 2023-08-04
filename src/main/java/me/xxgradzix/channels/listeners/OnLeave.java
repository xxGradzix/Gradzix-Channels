package me.xxgradzix.channels.listeners;

import me.xxgradzix.channels.Channels;
import me.xxgradzix.channels.PlayerInventoryEntityManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class OnLeave implements Listener {

    private PlayerInventoryEntityManager playerInventoryEntityManager;

    private Channels channels;

    public OnLeave(PlayerInventoryEntityManager playerInventoryEntityManager, Channels channels) {
        this.playerInventoryEntityManager = playerInventoryEntityManager;
        this.channels = channels;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        if(Channels.isDisabling) {
            return;
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(channels, new Runnable() {

            @Override
            public void run() {
                if (event.getPlayer() != null) {
                    Player p = event.getPlayer();
                    ItemStack[] inventory = channels.getInventoryDataHandler().getInventory(p);
                    ItemStack[] armor = channels.getInventoryDataHandler().getArmor(p);
                    ItemStack[] enderChest = channels.getInventoryDataHandler().getEnderChest(p);
                    channels.getInventoryDataHandler().onDataSaveFunction(p, true, true, inventory, armor, enderChest);
                }
            }
        }, 2L);
    }
}
