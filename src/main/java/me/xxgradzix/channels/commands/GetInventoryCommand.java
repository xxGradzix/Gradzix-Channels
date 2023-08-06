package me.xxgradzix.channels.commands;

import me.xxgradzix.channels.Channels;
import me.xxgradzix.channels.PlayerInventoryEntityManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetInventoryCommand implements CommandExecutor {

    private PlayerInventoryEntityManager playerInventoryEntityManager;
    private Channels channels;
    public GetInventoryCommand(PlayerInventoryEntityManager playerInventoryEntityManager, Channels channels) {
        this.playerInventoryEntityManager = playerInventoryEntityManager;
        this.channels = channels;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Ta komenda jest dostępna tylko dla graczy!");
            return true;
        }

        Player player = (Player) sender;

        return true;

    }
}
