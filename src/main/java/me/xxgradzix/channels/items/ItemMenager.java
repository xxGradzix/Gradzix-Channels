package me.xxgradzix.channels.items;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemMenager {


    public static void init() {
    }

    public static ItemStack createServerIcon(int channelNum, String serverName, int playerNumber, boolean isOnline, boolean isCurrent) {
        ItemStack item;

        if(isOnline) {
            item = new ItemStack(Material.GREEN_CONCRETE, channelNum);
        } else {
            item = new ItemStack(Material.RED_CONCRETE, channelNum);
        }
        ItemMeta meta = item.getItemMeta();

        if(isOnline) {
            meta.setDisplayName(ChatColor.GRAY + "Kanał: " + ChatColor.GREEN + serverName);
        } else {
            meta.setDisplayName(ChatColor.GRAY + "Kanał: " + ChatColor.RED + serverName);
        }

        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY + "Ilość graczy: " + playerNumber);

        if(isCurrent) {
            lore.add(ChatColor.GRAY + "Aktualnie znajdujesz sie na tym kanale");
        } else {
            if(isOnline) {
                lore.add(ChatColor.GRAY + "Ten serwer jest: " + ChatColor.GREEN + "ONLINE");
            } else {
                lore.add(ChatColor.GRAY + "Ten serwer jest: " + ChatColor.RED + "OFFLINE");
            }
        }

        meta.setLore(lore);
//        meta.addEnchant(Enchantment.LUCK, 1, true);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        return item;
    }

    public static String convertColorText(String text) {
        StringBuilder convertedText = new StringBuilder();
        String[] parts = text.split("&#");

        for (String part : parts) {
            if (!part.isEmpty()) {
                String colorCode = part.substring(0, 6);
                String letter = part.substring(6);

                ChatColor color = ChatColor.of("#" + colorCode);

                convertedText.append(color).append(letter);
            }
        }

        return convertedText.toString();
    }
}
