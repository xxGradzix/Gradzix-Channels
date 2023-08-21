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
import org.bukkit.Bukkit;
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
//        System.out.println("test command start - " + player.getName());

        requestServerPlayerCount(player);

        return true;
    }
    public void chooseChannel(Player player, HashMap<String, ServerPlayerCountInfo> servers, String currentServer) {

        {
        Gui gui = Gui.gui()
                .title(Component.text(ChatColor.GREEN + ChatColor.BOLD.toString() + "CHANNELS " + ChatColor.GRAY + ChatColor.ITALIC + "(/channels)"))
                .rows(1)
//                .type(GuiType.HOPPER)
                .disableAllInteractions()
                .create();

        int serverNum = 1;

        int slot;
        int slotIncrementation;

        slot = 2;
        slotIncrementation= 1;
//        if (Config.getServerNameList().size() % 4 == 0) {
//            slot = 10;
//            slotIncrementation = 2;
//        } else if (Config.getServerNameList().size() % 3 == 0) {
//            slot = 11;
//            slotIncrementation = 2;
//        } else {
//            slot = 11;
//            slotIncrementation = 4;
//        }

        for (String server : Config.getServerNameList()) {

            ServerPlayerCountInfo serverPlayerCountInfo = servers.getOrDefault(server, null);

            if (serverPlayerCountInfo == null) continue;

            boolean isCurrent;

            if (server.equalsIgnoreCase(currentServer)) {

                isCurrent = true;
            } else {
                isCurrent = false;
            }

            GuiItem guiItem = ItemBuilder.from(ItemMenager.createServerIcon(serverNum, server, serverPlayerCountInfo.getCount(), serverPlayerCountInfo.isOnline(), isCurrent)).asGuiItem((action) -> {
                if (!isCurrent) {
                    if (serverPlayerCountInfo.isOnline()) {
                        connect(player, server);
                    } else {
                        gui.close(player);
                        player.sendMessage(ChatColor.GRAY + "Kanal " + server + " jest obecnie wyłączony");
                    }

                }

            });

//            gui.addItem(guiItem);

            gui.setItem(slot, guiItem);
            slot += slotIncrementation;
        }
        while (slot < 7) {
            GuiItem guiItem = ItemBuilder.from(ItemMenager.createServerIcon(serverNum, "serwer nieaktywny", 0, false, false)).asGuiItem((action) -> {
                gui.close(player);
                player.sendMessage(ChatColor.GRAY + "Ten kanal jest obecnie nieaktywny");
            });
            gui.setItem(slot, guiItem);
            slot+= slotIncrementation;
        }
        gui.open(player);
    }
//        Gui gui = Gui.gui()
//                .title(Component.text(ChatColor.GREEN + ChatColor.BOLD.toString() + "CHANNELS " + ChatColor.GRAY + ChatColor.ITALIC + "(/channels)"))
//                .type(GuiType.HOPPER)
//                .disableAllInteractions()
//                .create();
//
//        int serverNum = 1;
//
//        int slot;
//        int slotIncrementation;
//        if (Config.getServerNameList().size() % 5 == 0) {
//            slot = 0;
//            slotIncrementation = 1;
//        } else if (Config.getServerNameList().size() % 4 == 0) {
//            slot = 0;
//            slotIncrementation = 1;
//        } else if (Config.getServerNameList().size() % 3 == 0) {
//            slot = 0;
//            slotIncrementation = 2;
//        } else {
//            slot = 1;
//            slotIncrementation = 2;
//        }
//
//        for (String server : Config.getServerNameList()) {
//
//            ServerPlayerCountInfo serverPlayerCountInfo = servers.getOrDefault(server, null);
//
//            if (serverPlayerCountInfo == null) continue;
//
//            boolean isCurrent;
//
//            if (server.equalsIgnoreCase(currentServer)) {
//                isCurrent = true;
//            } else {
//                isCurrent = false;
//            }
//
//            GuiItem guiItem = ItemBuilder.from(ItemMenager.createServerIcon(serverNum, server, serverPlayerCountInfo.getCount(), serverPlayerCountInfo.isOnline(), isCurrent)).asGuiItem((action) -> {
//                player.sendMessage("dupaadad");
//                if (!isCurrent) {
//                    if (serverPlayerCountInfo.isOnline()) {
//                        connect(player, server);
//                    } else {
//                        gui.close(player);
//                        player.sendMessage(ChatColor.GRAY + "Kanal " + server + " jest obecnie wyłączony");
//                    }
//                }
//            });
//            gui.setItem(slot, guiItem);
//            slot += slotIncrementation;
//        }
//        gui.open(player);
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

                    boolean isOnline = in.readBoolean();

//                    if(Config.getOfflineServerList().contains(server)) {
//                        isOnline = false;
//                    }

                    ServerPlayerCountInfo serverPlayerCountInfo = new ServerPlayerCountInfo(server, serverPlayerCount, isOnline);
                    servers.put(server, serverPlayerCountInfo);
                }
                String currentServer = in.readUTF();
                String playerName = in.readUTF();
                chooseChannel(Bukkit.getServer().getPlayer(playerName), servers, currentServer);
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
