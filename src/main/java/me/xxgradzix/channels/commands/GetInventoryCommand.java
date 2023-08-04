package me.xxgradzix.channels.commands;

import me.xxgradzix.channels.PlayerInventoryEntityManager;
import me.xxgradzix.channels.items.ItemMenager;
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

//        for(Server server : Channels.channels) {
//            player.sendMessage(server.getName());
//        }


//        ItemStack[] inventory = playerInventoryEntityManager.getPlayerInventoryEntityById(uuid).getInventory();
        player.getInventory().addItem(ItemMenager.firework);
//        if(inventory == null) {
//
//            player.sendMessage("Invnetory jest null");
//            return true;
//        }
//
//        for(ItemStack item : inventory) {
//            if(item == null) continue;
//
//            player.getInventory().addItem(item);
//        }
        return true;
    }
}
