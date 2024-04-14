package me.xxgradzix.channelsbungee.commands;

import me.xxgradzix.channelsbungee.Channels_Bungee;
import me.xxgradzix.channelsbungee.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;

public class ServerOnOffCommand extends Command implements TabExecutor {

    private ConfigManager configManager = Channels_Bungee.configManager;

    public ServerOnOffCommand() {
        super("channeltoggle");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length == 2) {

            String serverName = args[0];


            boolean isServerValid = false;

            for(String network : configManager.getServerNetworksNames()) {
                for (String server : configManager.getServerNetworkChannels(network)) {
                    if(serverName.equalsIgnoreCase(server)) {
                        isServerValid = true;
                    }

                }
            }
            if(!isServerValid) {
                sender.sendMessage("Taki channel nie istnieje");
                return;
            }

            String status = args[1];

            if(status.equalsIgnoreCase("on") || status.equalsIgnoreCase("online")) {

                Channels_Bungee.configManager.removeOfflineChannel(serverName);
                sender.sendMessage(ChatColor.GRAY +
                        "Status serwera " + serverName +
                        " jest teraz " + ChatColor.GREEN +
                        "ONLINE");
            } else if(status.equalsIgnoreCase("off") || status.equalsIgnoreCase("offline")) {

                Channels_Bungee.configManager.addOfflineChannel(serverName);
                sender.sendMessage(ChatColor.GRAY +
                        "Status serwera " + serverName +
                        " jest teraz " + ChatColor.RED +
                        "OFFLINE");

            } else {
                sender.sendMessage(ChatColor.GRAY +
                        "Status serwera mozesz ustawic tylko na " +ChatColor.GREEN +
                        " ON " + ChatColor.GRAY +
                        " albo " + ChatColor.RED +
                        "OFF");
                return;
            }

        } else {
            sender.sendMessage("Poprawne uzycie komendy to /server <channelName> <ON/OFF>");
            return;
        }

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {

        ArrayList<String> list = new ArrayList<>();
        if(strings.length == 1) {
            for(String network : configManager.getServerNetworksNames()) {
//                if(network.equalsIgnoreCase("offlineChannels")) continue;
                for (String server : configManager.getServerNetworkChannels(network)) {
                    list.add(server);
                }
            }
        }

        if(strings.length == 2) {
            list.add("on");
            list.add("off");
        }
        return list;
    }
}
