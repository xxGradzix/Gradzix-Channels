package me.xxgradzix.channelsbungee.commands;

import me.xxgradzix.channelsbungee.Channels_Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoinServerChannelCommand extends Command implements TabExecutor {

//    private Configuration configuration;
    public JoinServerChannelCommand() {
        super("joinChannelsServer");
//        this.configuration = configuration;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof ProxiedPlayer)) return;

        if(args.length == 1) {

            String channelsServer = args[0];

            connectPlayerToLeastPlayersServer((ProxiedPlayer) sender, channelsServer);

//            List<String> serverList = configuration.getStringList(channelsServer);
//            List<String> serverList = Channels_Bungee.configManager.getOnlineChannelsInNetwork(channelsServer);
//
//            if(serverList == null || serverList.isEmpty()) {
//                sender.sendMessage(ChatColor.RED + "Aktualnie żaden kanał nie jest online");
////                sender.sendMessage("sieć kanałów na jaką próbujesz sie połączyć nie istnieje");
////                sender.sendMessage("aktywne sieci na które możesz sie połączyć:");
////                for(String serverNetwork : Channels_Bungee.configManager.getServerNetworksNames()) {
////                    if(serverNetwork.equalsIgnoreCase("offlineChannels")) continue;
////                    sender.sendMessage("- " + serverNetwork);
////                }
//                return;
//            }
//            Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();
//
//            if(!servers.keySet().containsAll(serverList)) {
//                sender.sendMessage("Nie można połączyć Cie z tą siecią serwerów, zgłoś ten błąd administracji");
//                return;
//            }
//
//            String leastPlayersServer = serverList.get(0);
//
////            List<String> offlineServers = Channels_Bungee.configManager.getOfflineChannels();
////            List<String> onlineServers = Channels_Bungee.configManager.getOnlineChannelsInNetwork(channelsServer);
//
//            for(String server : serverList) {
////                if(offlineServers.contains(server)) continue;
//
//                if(servers.get(server).getPlayers().size() < servers.get(leastPlayersServer).getPlayers().size()) {
//                    leastPlayersServer = server;
//                }
//            }
//
//            ((ProxiedPlayer) sender).connect(servers.get(leastPlayersServer));

        } else {
            sender.sendMessage("/joinChannelsServer <serverNetwork>");
        }
    }
    public static void connectPlayerToLeastPlayersServer(ProxiedPlayer player, String serverNetwork) {

        List<String> serverList = Channels_Bungee.configManager.getOnlineChannelsInNetwork(serverNetwork);

        if(serverList == null || serverList.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Aktualnie żaden kanał nie jest online");
            return;
        }
        Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();

        if(!servers.keySet().containsAll(serverList)) {
            player.sendMessage("Nie można połączyć Cie z tą siecią serwerów, zgłoś ten błąd administracji");
            return;
        }

        String leastPlayersServer = serverList.get(0);

        for(String server : serverList) {
            if(servers.get(server).getPlayers().size() < servers.get(leastPlayersServer).getPlayers().size()) {
                leastPlayersServer = server;
            }
        }

        player.connect(servers.get(leastPlayersServer));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
        if(strings.length == 1) {
            ArrayList<String> list = new ArrayList<>();

            for(String serverNetwork : Channels_Bungee.configManager.getServerNetworksNames()) {
                if(serverNetwork.equalsIgnoreCase("offlineChannels")) continue;
                list.add(serverNetwork);
            }

            return list;
        }
        return null;
    }
}
