package me.xxgradzix.channels.persisters;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import me.xxgradzix.channels.utills.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class LocationPersister extends StringType {


    private static final LocationPersister instance = new LocationPersister();

    public static LocationPersister getSingleton() {
        return instance;
    }
    protected LocationPersister() {
        super(SqlType.STRING, new Class<?>[]{Location.class});
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException {
        if (javaObject != null && javaObject instanceof Location) {
            Location location = (Location) javaObject;
            return LocationUtils.locationToBase64(location);
        }
        return null;
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        if (sqlArg != null && sqlArg instanceof String) {
            String jsonString = (String) sqlArg;
            try {
                return LocationUtils.locationFromBase64(jsonString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    public static String getSerializedLocation(Location loc) {

        return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getWorld().getUID();
    }
    public static Location getDeserializedLocation(String s) {
        String [] parts = s.split(";");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        UUID u = UUID.fromString(parts[3]);
//        String name = parts[3];
        World w = Bukkit.getServer().getWorld(u);
        return new Location(w, x, y, z);
    }

}
