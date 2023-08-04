package me.xxgradzix.channels;

import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InventoryUtils {
	
	public static String toBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
        
            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());
        
            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
        
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }    
    }
	
	public static Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());
    
            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            
            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
	
	 public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
	    	try {
	            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
	            
	            // Write the size of the inventory
	            dataOutput.writeInt(items.length);
	            
	            // Save every element in the list
	            for (int i = 0; i < items.length; i++) {
	                dataOutput.writeObject(items[i]);
	            }
	            
	            // Serialize that array
	            dataOutput.close();
	            return Base64Coder.encodeLines(outputStream.toByteArray());
	        } catch (Exception e) {
	            throw new IllegalStateException("Unable to save item stacks.", e);
	        }
	    }
	
	public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
    	try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
    
            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
            	items[i] = (ItemStack) dataInput.readObject();
            }
            
            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
	
	public static String saveModdedStacksData(ItemStack[] itemStacks) {
	    StringBuilder stringBuilder = new StringBuilder();
	    for (int i = 0; i < itemStacks.length; i++)
	    {
	      if (i > 0) {
	        stringBuilder.append(";");
	      }
	      if ((itemStacks[i] != null) && (itemStacks[i].getType() != Material.AIR)) {
	        try
	        {
	          stringBuilder.append(StreamSerializer.getDefault().serializeItemStack(itemStacks[i]));
	        }
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	      }
	    }
	    String string = stringBuilder.toString();
	    return string;
	  }
	
	public static ItemStack[] restoreModdedStacks(String string) {
	    String[] strings = string.split(";");
	    ItemStack[] itemStacks = new ItemStack[strings.length];
	    for (int i = 0; i < strings.length; i++) {
	      if (!strings[i].equals("")) {
			  itemStacks[i] = StreamSerializer.getDefault().deserializeItemStack(strings[i]);
		  }
	    }
	    return itemStacks;
	  }

}