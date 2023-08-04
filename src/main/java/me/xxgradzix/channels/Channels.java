package me.xxgradzix.channels;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import me.xxgradzix.channels.commands.GetInventoryCommand;
import me.xxgradzix.channels.entities.PlayerInventoryEntity;
import me.xxgradzix.channels.items.ItemMenager;
import me.xxgradzix.channels.listeners.OnJoin;
import me.xxgradzix.channels.listeners.OnLeave;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Channels extends JavaPlugin {

    public static boolean isDisabling = false;

    private static InventoryDataHandler inventoryDataHandler;

//    public static ArrayList<Server> channels;
    private String databaseUrl = "jdbc:mysql://localhost:3306/channels";

    private ConnectionSource connectionSource;
    private static PlayerInventoryEntityManager playerInventoryEntityManager;

    public PlayerInventoryEntityManager getPlayerInventoryEntityManager() {
        return playerInventoryEntityManager;
    }

    public Channels() throws SQLException {

        ItemMenager.init();
//        this.playerInventoryEntityManager = new PlayerInventoryEntityManager(connectionSource);

        this.connectionSource = new JdbcConnectionSource(databaseUrl, "root", "");

        TableUtils.createTableIfNotExists(connectionSource, PlayerInventoryEntity.class);

        this.playerInventoryEntityManager = new PlayerInventoryEntityManager(connectionSource);
    }

    public InventoryDataHandler getInventoryDataHandler() {
        return inventoryDataHandler;
    }
    @Override
    public void onEnable() {

//        channels.add(this.getServer());
        System.out.println("plugin v 1");
        inventoryDataHandler = new InventoryDataHandler(this, playerInventoryEntityManager);

        getServer().getPluginManager().registerEvents(new OnJoin(playerInventoryEntityManager, this), this);
        getServer().getPluginManager().registerEvents(new OnLeave(playerInventoryEntityManager, this), this);

        getCommand("getinventory").setExecutor(new GetInventoryCommand(playerInventoryEntityManager));
    }

    @Override
    public void onDisable() {

        isDisabling = true;
        Bukkit.getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);

    }
}
