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

        if(isCurrent) {
            item = new ItemStack(Material.ORANGE_WOOL, 1);
        } else {
            if(isOnline) {
                item = new ItemStack(Material.LIME_WOOL, 1);
            } else {
                item = new ItemStack(Material.RED_WOOL, 1);
            }
        }
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "Kanał: " + ChatColor.WHITE + serverName);
//        if(isOnline) {
//            meta.setDisplayName(ChatColor.GRAY + "Kanał: " + ChatColor.GREEN + serverName);
//        } else {
//            meta.setDisplayName(ChatColor.GRAY + "Kanał: " + ChatColor.RED + serverName);
//        }

        ArrayList<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Online: " + ChatColor.DARK_GREEN + playerNumber);
        lore.add(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "TPS: " + ChatColor.DARK_GREEN + (isOnline ? "20" : "0"));
        lore.add(" ");

        if(isCurrent) {
            lore.add(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD + "Znajdujesz obecnie się na tym sektorze");
        } else {
            if(isOnline) {
                lore.add(ChatColor.DARK_GRAY + "» " + ChatColor.GREEN + "Kliknij aby przejść na ten sektor");
            } else {
                lore.add(ChatColor.DARK_GRAY + "» " + ChatColor.RED + "Sektor jest wyłączony");
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
