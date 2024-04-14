package me.xxgradzix.channelsbungee;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ConfigManager {

    private Configuration configuration;
    private File file;


    public ConfigManager(Configuration configuration, File file) {
        this.configuration = configuration;
        this.file = file;
    }

    public List<String> getServerNetworkChannels(String networkName) {
        return configuration.getStringList(networkName);
    }
    public Collection<String> getServerNetworksNames() {
        Collection<String> serverNetworkNames = configuration.getKeys();
        serverNetworkNames.remove("offlineChannels");
        return serverNetworkNames;
    }
    public List<String> getOfflineChannels() {
        return configuration.getStringList("offlineChannels");
    }
    public void addOfflineChannel(String channel) {
        List<String> channels = getOfflineChannels();
        channels.add(channel);
        configuration.set("offlineChannels", channels);
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration,file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setOfflineChannels(List<String> channels) {

        configuration.set("offlineChannels", channels);
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration,file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void removeOfflineChannel(String channel) {
        List<String> channels = getOfflineChannels();
        channels.remove(channel);
        configuration.set("offlineChannels", channels);
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration,file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getOnlineChannelsInNetwork(String network) {
        List<String> onlineChannels = getServerNetworkChannels(network);
        onlineChannels.removeAll(getOfflineChannels());
        return onlineChannels;
    }
    public int getOnlineChannelsAmountInNetwork(String network) {
        List<String> onlineChannels = getServerNetworkChannels(network);
        onlineChannels.removeAll(getOfflineChannels());
        return onlineChannels.size();
    }

}
