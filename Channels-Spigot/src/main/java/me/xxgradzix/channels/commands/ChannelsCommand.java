package me.xxgradzix.channels.commands;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.stream.Collectors;

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

        {
        Gui gui = Gui.gui()
                .title(Component.text(ChatColor.GREEN + ChatColor.BOLD.toString() + "CHANNELS " + ChatColor.GRAY + ChatColor.ITALIC + "(/channels)"))
                .rows(1)
                .disableAllInteractions()
                .create();

        int serverNum = 1;

        int slot;
        int slotIncrementation;

        slot = 2;
        slotIncrementation= 1;

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
                    if(!serverPlayerCountInfo.isOnline()) {
                        gui.close(player);
                        player.sendMessage(ChatColor.GRAY + "Kanal " + server + " jest obecnie wyłączony");
                        return;
                    }

                    if(!isPlayerInProperRegion(player)) {
                        gui.close(player);
                        player.sendMessage(ChatColor.GRAY + "Nie możesz zmienić kanału podczas gdy jesteś w tym regionie");
                        if(player.isOp()) {
                            player.sendMessage(ChatColor.GRAY + "Jeśli jesteś adminem to zmień region w którym się znajdujesz");
                            player.sendMessage("Nazwa regionu dozwolonego to " + Channels.PROPER_REGION_NAME);
                            player.sendMessage("Nazwa regionu zakazanego to " + Channels.FORBIDDEN_REGION_NAME);
                        }
                        return;
                    }

                    connect(player, server);
                }

            });

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
    }

    private boolean isPlayerInProperRegion(Player player) {
        Location location = player.getLocation();

        ApplicableRegionSet regionSet = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(location));

        boolean isInForbiddenRegion = regionSet.getRegions().stream().map(ProtectedRegion::getId).collect(Collectors.toSet()).contains(Channels.FORBIDDEN_REGION_NAME);

        boolean isInProperRegion = regionSet.getRegions().stream().map(ProtectedRegion::getId).collect(Collectors.toSet()).contains(Channels.PROPER_REGION_NAME);

        return isInProperRegion && !isInForbiddenRegion;
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
