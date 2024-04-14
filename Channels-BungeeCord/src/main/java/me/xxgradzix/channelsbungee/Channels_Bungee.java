package me.xxgradzix.channelsbungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.xxgradzix.channelsbungee.commands.JoinServerChannelCommand;
import me.xxgradzix.channelsbungee.commands.ServerOnOffCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public final class Channels_Bungee extends Plugin implements Listener {


    private File file;
    private Configuration configuration;

    public static ConfigManager configManager;
    public void setupConfig(){
        file = new File(ProxyServer.getInstance().getPluginsFolder()+ "/config.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();
                configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

                ArrayList<String> servers = new ArrayList<>();
                servers.add("serverChannel1");
                servers.add("serverChannel2");

                configuration.set("serverExample", servers);

                ArrayList<String> offline = new ArrayList<>();
                offline.add("serverChannel1");
                offline.add("serverChannel2");

                configuration.set("offlineChannels", offline);


                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration,file);

            } else {
                configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration,file);
            }
            configManager = new ConfigManager(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {

        setupConfig();

        enablingChannelDisabling();

        getProxy().registerChannel("myplugin:playercount");
        getProxy().registerChannel("myplugin:joinPlayer");

        getProxy().getPluginManager().registerCommand(this, new JoinServerChannelCommand());
        getProxy().getPluginManager().registerCommand(this, new ServerOnOffCommand());
        getProxy().getPluginManager().registerListener(this, this);
//        getProxy().getPluginManager().registerListener(this, new ChannelTogglingMechanism());
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {

        if (event.getTag().equals("myplugin:joinPlayer")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
            String subChannel = null;

            try {
                subChannel = in.readUTF();
                JoinServerChannelCommand.connectPlayerToLeastPlayersServer((ProxiedPlayer) event.getReceiver(), subChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!event.getTag().equals("myplugin:playercount")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput( event.getData() );
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("ServerPlayerCounts")) {
            if ( event.getReceiver() instanceof ProxiedPlayer)
            {
                ProxiedPlayer receiver = (ProxiedPlayer) event.getReceiver();

                sendCustomData(receiver);
            }
        }
    }
    public void sendCustomData(ProxiedPlayer player) {

        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        if ( networkPlayers == null || networkPlayers.isEmpty() )
        {
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF( "ServerPlayerCounts" );
        out.writeInt(getProxy().getServers().keySet().size());
        for(String server : getProxy().getServers().keySet()) {

            int playerCount = getProxy().getServers().get(server).getPlayers().size();

            out.writeUTF(server);
            out.writeInt(playerCount);

            boolean isOnline = true;
            if(Channels_Bungee.configManager.getOfflineChannels().contains(server)) {
                isOnline = false;
            }
            out.writeBoolean(isOnline);

        }
        // current server
        out.writeUTF(player.getServer().getInfo().getName());
        out.writeUTF(player.getName());
        player.getServer().getInfo().sendData( "myplugin:playercount", out.toByteArray() );
    }
    public void enablingChannelDisabling() {
        for (String network : configManager.getServerNetworksNames()) {
            if(network.equalsIgnoreCase("offlineChannels")) continue;
            List<String> channels = configManager.getServerNetworkChannels(network);
            List<String> offlineChannels = new ArrayList<>();
            for(int i = 1; i < channels.size(); i++) {
                offlineChannels.add(channels.get(i));
//                System.out.println("ch -  " + channels.get(i));
//                configManager.addOfflineChannel(channels.get(i));
            }
            configManager.setOfflineChannels(offlineChannels);
        }
    }
}
