package me.xxgradzix.channels.items;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemMenager {

    public static ItemStack firework;

    public static void init() {
        createWand();
    }

    private static void createWand() {
        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET, 1);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));
        lore.add(convertColorText("&#084cfbN&#2350eca&#3e54dcj&#5a58cdd&#755cbdl&#9060aeu&#ab649ez&#c7688fs&#e26c7fz&#fd7070a&#ef8077n&#e1907fa&#d39f86z&#c5af8dw&#b8bf95a&#aacf9cp&#9cdea3r&#8eeeabz&#80feb2e&#82fcb4d&#84fab6m&#87f8b7i&#89f6b9o&#8bf5bbt&#8df3bdu&#90f1bem&#92efc0o&#94edc2z"));

        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
        meta.addEnchant(Enchantment.SWEEPING_EDGE, 1, true);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        firework = item;
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
