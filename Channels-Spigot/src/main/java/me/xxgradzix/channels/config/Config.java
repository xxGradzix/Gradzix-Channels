package me.xxgradzix.channels.config;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Config {

    private static File file;
    private static FileConfiguration customFile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Channels").getDataFolder(), "config.yml");
        if(!file.exists()) {
            try{
                file.createNewFile();
            } catch (IOException e) {
                //
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getCustomFile() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Couldn't save file");
        }
    }
    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static void setChannelList(List<String> channelsList) {
        getCustomFile().set("channels", channelsList);
        save();
    }

    public static List<String> getServerNameList() {
        return getCustomFile().getStringList("channels");
    }
    public static List<String> getOfflineServerList() {
        return getCustomFile().getStringList("offlineChannels");
    }

    public static JdbcConnectionSource getConnection() throws SQLException {

        String databaseUrl = getCustomFile().getString("database.url");
        String user = getCustomFile().getString("database.user");
        String password = getCustomFile().getString("database.password");

        return new JdbcConnectionSource(databaseUrl, user, password);

    }



}
