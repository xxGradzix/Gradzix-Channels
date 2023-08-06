package me.xxgradzix.channels.commands;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.xxgradzix.channels.Channels;
import me.xxgradzix.channels.config.Config;
import me.xxgradzix.channels.entities.ServerPlayerCountInfo;
import me.xxgradzix.channels.items.ItemMenager;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ChannelsCommand implements CommandExecutor, PluginMessageListener {

    private Channels channels;

    public ChannelsCommand(Channels channels) {
        this.channels = channels;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command");
            return true;
        }
        Player player = (Player) sender;
        requestServerPlayerCount(player);

        return true;
    }
    public void chooseChannel(Player player, HashMap<String, ServerPlayerCountInfo> servers, String currentServer) {
                Gui gui = Gui.gui()
                .title(Component.text(ChatColor.GREEN + ChatColor.BOLD.toString() + "CHANNELS " + ChatColor.GRAY + ChatColor.ITALIC + "(/channels)"))
                .rows(3)
                .disableAllInteractions()
                .create();

        int serverNum = 1;

        int slot;
        int slotIncrementation;
        if(Config.getServerNameList().size() % 4 == 0) {
            slot = 10;
            slotIncrementation = 2;
        } else if (Config.getServerNameList().size() % 3 == 0) {
            slot = 11;
            slotIncrementation = 2;
        } else {
            slot = 11;
            slotIncrementation = 4;
        }

        for (String server : Config.getServerNameList()) {

            ServerPlayerCountInfo serverPlayerCountInfo = servers.getOrDefault(server, null);

            if(serverPlayerCountInfo == null) continue;

            boolean isCurrent;

            if(server.equalsIgnoreCase(currentServer)) {

                isCurrent = true;
            } else {
                isCurrent = false;
            }

            GuiItem guiItem = ItemBuilder.from(ItemMenager.createServerIcon(serverNum, server, serverPlayerCountInfo.getCount(), serverPlayerCountInfo.isOnline(), isCurrent)).asGuiItem((action) -> {
                if(!isCurrent) {
                    connect(player, server);
                }

            });

            gui.setItem(slot, guiItem);
            slot += slotIncrementation;
        }
        gui.open(player);
    }
    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {

        if (channel.equals("myplugin:playercount")) {

            ByteArrayDataInput in = ByteStreams.newDataInput(message);

            String subChannel = in.readUTF();

            if (subChannel.equalsIgnoreCase("ServerPlayerCounts")) {

                HashMap<String, ServerPlayerCountInfo> servers = new HashMap<>();

                int size = in.readInt();
                for(int i = 0; i< size; i++) {
                    String server = in.readUTF();
                    int serverPlayerCount = in.readInt();
                    ServerPlayerCountInfo serverPlayerCountInfo = new ServerPlayerCountInfo(server, serverPlayerCount, true);
                    servers.put(server, serverPlayerCountInfo);
                }
                String currentServer = in.readUTF();
                chooseChannel(player, servers, currentServer);
            }
        }
    }
    public void connect(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);
        player.sendPluginMessage(channels, "BungeeCord", output.toByteArray());
    }
    public void checkPlayerServer(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetPlayerServer");
        out.writeUTF(player.getName());
        player.sendPluginMessage(channels, "BungeeCord", out.toByteArray());
    }
    public void requestServerPlayerCount(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ServerPlayerCounts");
        player.sendPluginMessage(channels, "myplugin:playercount", output.toByteArray());
    }
}
