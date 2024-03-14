package me.xxgradzix.channels;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import me.xxgradzix.channels.commands.ChannelsCommand;
import me.xxgradzix.channels.config.Config;
import me.xxgradzix.channels.entities.PlayerInventoryEntity;
import me.xxgradzix.channels.items.ItemMenager;
import me.xxgradzix.channels.listeners.DropItem;
import me.xxgradzix.channels.listeners.InventoryClick;
import me.xxgradzix.channels.listeners.OnJoin;
import me.xxgradzix.channels.listeners.OnLeave;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public final class Channels extends JavaPlugin {


    public static final String PROPER_REGION_NAME = "channels";
    public static final String FORBIDDEN_REGION_NAME = "noChannels";
    public static boolean isDisabling = false;

    private static InventoryDataHandler inventoryDataHandler;

    private String databaseUrl = "jdbc:mysql://185.16.39.57:3306/s373_database";
//    private String databaseUrl = "jdbc:mysql://localhost:3306/channels";

    private ConnectionSource connectionSource;
    private static PlayerInventoryEntityManager playerInventoryEntityManager;

    public PlayerInventoryEntityManager getPlayerInventoryEntityManager() {
        return playerInventoryEntityManager;
    }

    public void configureDB() throws SQLException {

        ItemMenager.init();

        this.connectionSource = new JdbcConnectionSource(databaseUrl, "u373_QspyTolB9K", "wOpx=TEr@uFg7t=G4v2yu3nO");

//        this.connectionSource = Config.getConnection();

        TableUtils.createTableIfNotExists(connectionSource, PlayerInventoryEntity.class);

        this.playerInventoryEntityManager = new PlayerInventoryEntityManager(connectionSource);
    }

    public InventoryDataHandler getInventoryDataHandler() {
        return inventoryDataHandler;
    }
    @Override
    public void onEnable() {

        try {
            configureDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!LocalDate.now().isBefore(LocalDate.of(2024, 05, 30))) {
            System.out.println("jezeli wyswietliła sie ta wiadomosc to skontaktuj sie z xxGradzix");
            return;
        }

        inventoryDataHandler = new InventoryDataHandler(this, playerInventoryEntityManager);

        getServer().getPluginManager().registerEvents(new OnJoin(playerInventoryEntityManager, this), this);
        getServer().getPluginManager().registerEvents(new OnLeave(playerInventoryEntityManager, this), this);
        getServer().getPluginManager().registerEvents(new DropItem(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(this), this);

        getCommand("channels").setExecutor(new ChannelsCommand(this));

        // kanaly bungee

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new ChannelsCommand(this));

        getServer().getMessenger().registerOutgoingPluginChannel(this, "myplugin:playercount");
        getServer().getMessenger().registerIncomingPluginChannel(this, "myplugin:playercount", new ChannelsCommand(this));

        // config

        Config.setup();
//        Config.getCustomFile().options().header("MySQL Database details\n");

//        Config.getCustomFile().addDefault("database.host", "localhost");
//        Config.getCustomFile().addDefault("database.port", 3306);
//        Config.getCustomFile().addDefault("database.databaseName", "channels");
//        Config.getCustomFile().addDefault("database.tableName", "channel_inventories");
//        Config.getCustomFile().addDefault("database.user", "root");
//        Config.getCustomFile().addDefault("database.password", "");
//        Config.getCustomFile().addDefault("database.sslEnabled", false);

        Config.getCustomFile().addDefault("database.url", "jdbc:mysql://localhost:3306/channels");
        Config.getCustomFile().addDefault("database.user", "root");
        Config.getCustomFile().addDefault("database.password", "");

//        Config.getCustomFile().options().header("Uwaga kazdy kanal w sieci serwerow musi miec ten sam plugin i ten sam plik konfiguracyjny\n");
//        Config.getCustomFile().options().header("WAZNE, kolejnosc channeli na liscie musi byc taka sama dla kazdego pliku konfiguracyjnego\n");

        ArrayList<String> servers = new ArrayList<>();
        servers.add("example");
        Config.getCustomFile().addDefault("channels", servers);

        Config.getCustomFile().options().copyDefaults(true);

        Config.save();

    }

    @Override
    public void onDisable() {

        isDisabling = true;
        Bukkit.getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);

    }
}
