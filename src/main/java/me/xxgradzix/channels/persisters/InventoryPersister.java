package me.xxgradzix.channels.persisters;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import me.xxgradzix.channels.utills.InventoryUtils;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.sql.SQLException;

public class InventoryPersister extends StringType {

    private static final InventoryPersister instance = new InventoryPersister();

    public static InventoryPersister getSingleton() {
        return instance;
    }


    protected InventoryPersister() {
        super(SqlType.STRING, new Class<?>[]{Inventory.class});
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException {
        if (javaObject != null && javaObject instanceof Inventory) {
            Inventory inventory = (Inventory) javaObject;
            return InventoryUtils.toBase64(inventory);
        }
        return null;
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        if (sqlArg != null && sqlArg instanceof String) {
            String jsonString = (String) sqlArg;
            try {
                return InventoryUtils.fromBase64(jsonString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
