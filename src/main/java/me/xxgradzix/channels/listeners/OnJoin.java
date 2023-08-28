package me.xxgradzix.channels.listeners;

import me.xxgradzix.channels.Channels;
import me.xxgradzix.channels.PlayerInventoryEntityManager;
import me.xxgradzix.channels.objects.SyncCompleteTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class OnJoin implements Listener {
    private PlayerInventoryEntityManager playerInventoryEntityManager;
    private Channels channels;
    public OnJoin(PlayerInventoryEntityManager playerInventoryEntityManager, Channels channels) {

        this.playerInventoryEntityManager = playerInventoryEntityManager;
        this.channels = channels;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        if(Channels.isDisabling) {
            System.out.println("PlayerJoinEvent stopped because plugin is disabling");
            return;
        }
        final Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();


        new BukkitRunnable() {
            @Override
            public void run() {
                if (player != null) {
                    if (player.isOnline() == true) {
                        channels.getInventoryDataHandler().onJoinFunction(player);
                        new SyncCompleteTask(channels, System.currentTimeMillis(), player, playerInventoryEntityManager).runTaskTimerAsynchronously(channels, 5L, 20L);
                    }
                }
            }

        }.runTaskLater(channels, 5L);


    }
}
