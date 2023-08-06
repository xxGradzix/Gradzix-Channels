package me.xxgradzix.channels.utills;

import org.bukkit.Location;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LocationUtils {

	 public static String locationToBase64(Location location) throws IllegalStateException {
	    	try {
	            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
	            

				dataOutput.writeObject(location);
	            
	            // Serialize that array
	            dataOutput.close();
	            return Base64Coder.encodeLines(outputStream.toByteArray());
	        } catch (Exception e) {
	            throw new IllegalStateException("Unable to save location.", e);
	        }
	    }
	
	public static Location locationFromBase64(String data) throws IOException {
    	try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            Location location = (Location) dataInput.readObject();

            dataInput.close();
            return location;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }


}