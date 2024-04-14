package me.xxgradzix.channels;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import me.xxgradzix.channels.entities.PlayerInventoryEntity;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class PlayerInventoryEntityManager {
    private Dao<PlayerInventoryEntity, UUID> playerInventoryEntityDao;

    public PlayerInventoryEntityManager(ConnectionSource connectionSource) {
        try {
            playerInventoryEntityDao = DaoManager.createDao(connectionSource, PlayerInventoryEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayerInventoryEntity(PlayerInventoryEntity player) {
        try {
            playerInventoryEntityDao.create(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createOrUpdatePlayerInventoryEntity(PlayerInventoryEntity playerInventoryEntity) {
        try {
            playerInventoryEntityDao.createOrUpdate(playerInventoryEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerInventoryEntity getPlayerInventoryEntityById(UUID playerUUID) {
        try {
            return playerInventoryEntityDao.queryForId(playerUUID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void updatePlayerInventoryEntity(PlayerInventoryEntity playerInventoryEntity) {
        try {
            playerInventoryEntityDao.update(playerInventoryEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayerInventoryEntity(PlayerInventoryEntity playerInventoryEntity) {
        try {
            playerInventoryEntityDao.delete(playerInventoryEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setPlayerInventoryEntitySyncStatus(Player player, boolean syncStatus) {
//        try {
            PlayerInventoryEntity playerInventoryEntity = getPlayerInventoryEntityById(player.getUniqueId());

            playerInventoryEntity.setSynCompleete(syncStatus);
            updatePlayerInventoryEntity(playerInventoryEntity);


//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
//    public void configurePlayerInventoryEntity(PlayerEntity player) {
//        try {
//            if(getPlayerEntityById(player.getUuid()) == null) {
//                playerDao.create(player);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
