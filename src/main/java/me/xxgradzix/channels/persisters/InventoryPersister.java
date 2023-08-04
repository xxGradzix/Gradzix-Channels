package me.xxgradzix.channels.serializer;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import me.xxgradzix.channels.InventoryUtils;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.sql.SQLException;

public class InventorySerializer extends StringType {

    protected InventorySerializer(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    protected InventorySerializer(SqlType sqlType) {
        super(sqlType);
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
