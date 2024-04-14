package main.java.me.xxgradzix.channelsbungee.listeners;

import me.xxgradzix.channelsbungee.Channels_Bungee;
import me.xxgradzix.channelsbungee.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;

public class ChannelTogglingMechanism implements Listener {
    private ConfigManager configManager = Channels_Bungee.configManager;

    int playerChannelToggleAmount = 4;
    double margin = 0.25;

    @EventHandler
    public void onPlayerJoin(ServerConnectEvent event) {

//        int playerOpenChannelAmount = playerChannelToggleAmount;
//        int playerOpenChannelAmount = 4;

//        playerOpenChannelAmount++;

        ProxiedPlayer player = event.getPlayer();



        String network = null;
        for (String n : configManager.getServerNetworksNames()) {
            if(configManager.getServerNetworkChannels(n).contains(event.getTarget().getName())) {
                network = n;
            }
        }
        if(network == null) {
            return;
        }

        if(configManager.getOnlineChannelsAmountInNetwork(network) == configManager.getServerNetworkChannels(network).size()) {
            return;
        }

        int players = 0;
        if(player.getServer() != null) {
            if(!configManager.getServerNetworkChannels(network).contains(player.getServer().getInfo().getName())) {
                players++;
            }
        }
        for (String channel : Channels_Bungee.configManager.getServerNetworkChannels(network)) {
            players += ProxyServer.getInstance().getServers().get(channel).getPlayers().size();
        }

        int amount = (int) ((configManager.getOnlineChannelsAmountInNetwork(network) * playerChannelToggleAmount) + (playerChannelToggleAmount * margin));

//        if(players >= configManager.getOnlineChannelsInNetwork(network) * playerOpenChannelAmount) {
        if(players >= amount) {
            for(String channel : Channels_Bungee.configManager.getServerNetworkChannels(network)) {
                if(configManager.getOfflineChannels().contains(channel)) {
                    configManager.removeOfflineChannel(channel);
                    ProxyServer.getInstance().broadcast(ChatColor.GRAY + "Ze względu na dużą ilość graczy został otworzony kanał " + ChatColor.GREEN +  channel);
                    break;
                }
            }
        }
    }
    @EventHandler
    public void onDisconnect(ServerDisconnectEvent event) {

        ProxiedPlayer player = event.getPlayer();


        int playerCloseChannelAmount = 4;
//        playerCloseChannelAmount-=1;


        String network = null;
        for (String n : configManager.getServerNetworksNames()) {
            if(configManager.getServerNetworkChannels(n).contains(event.getTarget().getName())) {
                network = n;
            }
        }
        if(network == null) {
            return;
        }

        int players = 0;
        for (String channel : Channels_Bungee.configManager.getServerNetworkChannels(network)) {

            players += ProxyServer.getInstance().getServers().get(channel).getPlayers().size();

        }

//        if(players < playerCloseChannelAmount)


        if(configManager.getOnlineChannelsAmountInNetwork(network) == 1) return;

        int amount = (int) (((configManager.getOnlineChannelsAmountInNetwork(network) - 1) * playerChannelToggleAmount) - (playerChannelToggleAmount * margin));

//        if(players  <= ((configManager.getOnlineChannelsInNetwork(network) + 0.5) * playerCloseChannelAmount)) {
        if(players  <= amount) {
            ArrayList<String> channels = (ArrayList<String>) Channels_Bungee.configManager.getServerNetworkChannels(network);
            for(int i = channels.size() - 1; i > 0; i--) {
                if(!configManager.getOfflineChannels().contains(channels.get(i))) {
                    configManager.addOfflineChannel(channels.get(i));
                    ProxyServer.getInstance().broadcast(ChatColor.GRAY + "Ze względu na małą ilość graczy kanał " + ChatColor.RED +  channels.get(i) + " został zamknięty");
                    break;
                }
//                System.out.println(channels.get(i));
            }
        }
//            List<String> channels = Channels_Bungee.configManager.getServerNetworkChannels(network);
//            Collections.reverse(channels);
//            for(String channel : Channels_Bungee.configManager.getServerNetworkChannels(network)) {
//                if(!configManager.getOfflineChannels().contains(channel)) {
//                    configManager.addOfflineChannel(channel);
//                    ProxyServer.getInstance().broadcast(ChatColor.GRAY + "Ze względu na małą ilość graczy kanał " + ChatColor.RED +  channel + " został zamknięty");
//                    break;
//                }
//            }
//        }

    }


}
