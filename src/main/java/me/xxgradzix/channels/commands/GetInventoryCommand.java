package me.xxgradzix.channels.commands;

import me.xxgradzix.channels.PlayerInventoryEntityManager;
import me.xxgradzix.channels.entities.PlayerInventoryEntity;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GetInventoryCommand implements CommandExecutor {

    private PlayerInventoryEntityManager playerInventoryEntityManager;
    public GetInventoryCommand(PlayerInventoryEntityManager playerInventoryEntityManager) {
        this.playerInventoryEntityManager = playerInventoryEntityManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        Player player = (Player) sender;

        UUID uuid = player.getUniqueId();
        PlayerInventoryEntity playerInventoryEntity = playerInventoryEntityManager.getPlayerInventoryEntityById(uuid);
        if(playerInventoryEntity != null && playerInventoryEntity.getLocation() != null) {
            System.out.printf("Nie null");
            Location location = playerInventoryEntity.getLocation();
            location.setWorld(player.getWorld());
            player.teleport(location);
        } else {
            System.out.println("NULL");
        }
        return true;
    }
}
