package me.xxgradzix.channels.commands;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.xxgradzix.channels.Channels;
import me.xxgradzix.channels.config.Config;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Bukkit.getServer;

public class ChannelsCommand implements CommandExecutor {

    private Channels channels;

    public ChannelsCommand(Channels channels) {
        this.channels = channels;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command");
            return true;
        }
        Player player = (Player) sender;

        Gui gui = Gui.gui()
                .title(Component.text(ChatColor.GREEN + ChatColor.BOLD.toString() + "CHANNELS " + ChatColor.GRAY + ChatColor.ITALIC + "(/channels)"))
                .rows(3)
                .disableAllInteractions()
                .create();
        getServer().dispatchCommand(player, "givesadle");
        int serverNum = 1;
        for(String serverName : Config.getServerNameList()) {
            player.sendMessage(serverName);
            ItemStack icon = new ItemStack(Material.CHEST_MINECART, serverNum);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName("Kanal " + serverNum);

            icon.setItemMeta(meta);
            GuiItem guiItem = ItemBuilder.from(icon).asGuiItem((action) -> {
//                player.sendPluginMessage(channels, "BungeeCord", ("server " + serverName).getBytes());
                player.performCommand("/server " + serverName);

//                getServer().dispatchCommand(player, "server " + serverName);
            });
            gui.addItem(guiItem);
            serverNum++;
        }

        gui.open(player);



        return true;
    }
}
