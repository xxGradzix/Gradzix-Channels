package me.xxgradzix.channels;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import me.xxgradzix.channels.commands.ChannelsCommand;
import me.xxgradzix.channels.commands.GetInventoryCommand;
import me.xxgradzix.channels.config.Config;
import me.xxgradzix.channels.entities.PlayerInventoryEntity;
import me.xxgradzix.channels.items.ItemMenager;
import me.xxgradzix.channels.listeners.OnJoin;
import me.xxgradzix.channels.listeners.OnLeave;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;

public final class Channels extends JavaPlugin {

    public static boolean isDisabling = false;

    private static InventoryDataHandler inventoryDataHandler;

    private String databaseUrl = "jdbc:mysql://localhost:3306/channels";

    private ConnectionSource connectionSource;
    private static PlayerInventoryEntityManager playerInventoryEntityManager;

    public PlayerInventoryEntityManager getPlayerInventoryEntityManager() {
        return playerInventoryEntityManager;
    }

    public Channels() throws SQLException {

        ItemMenager.init();

        this.connectionSource = new JdbcConnectionSource(databaseUrl, "root", "");

        TableUtils.createTableIfNotExists(connectionSource, PlayerInventoryEntity.class);

        this.playerInventoryEntityManager = new PlayerInventoryEntityManager(connectionSource);
    }

    public InventoryDataHandler getInventoryDataHandler() {
        return inventoryDataHandler;
    }
    @Override
    public void onEnable() {

        inventoryDataHandler = new InventoryDataHandler(this, playerInventoryEntityManager);

        getServer().getPluginManager().registerEvents(new OnJoin(playerInventoryEntityManager, this), this);
        getServer().getPluginManager().registerEvents(new OnLeave(playerInventoryEntityManager, this), this);

        getCommand("getinventory").setExecutor(new GetInventoryCommand(playerInventoryEntityManager, this));
        getCommand("channels").setExecutor(new ChannelsCommand(this));

        // kanaly bungee

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new ChannelsCommand(this));

        getServer().getMessenger().registerOutgoingPluginChannel(this, "myplugin:playercount");
        getServer().getMessenger().registerIncomingPluginChannel(this, "myplugin:playercount", new ChannelsCommand(this));

        Config.setup();
        Config.getCustomFile().options().header("Uwaga kazdy kanal w sieci serwerow musi miec ten sam plugin i ten sam plik konfiguracyjny\n");

//                "Lista nazw serwerow (kanalow) ktore maja byc ze soba polaczone\n" +
        Config.getCustomFile().options().header("WAZNE, kolejnosc channeli na liscie musi byc taka sama dla kazdego pliku konfiguracyjnego\n");

        Config.getCustomFile().options().copyHeader(true);
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
