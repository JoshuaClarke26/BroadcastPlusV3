package Dev.ScalerGames.BroadcastPlus.Methods.Gui;

import Dev.ScalerGames.BroadcastPlus.Files.Gui;
import Dev.ScalerGames.BroadcastPlus.Utils.Format;
import Dev.ScalerGames.BroadcastPlus.Utils.Messages;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemHandler {

    public static void addItem(Inventory inv, String name, String slot, Player p) {
        try {
            ItemStack item = new ItemStack(Material.valueOf(Gui.getGuiConfig().getString("Menus." + name + ".items." + slot + ".item")));
            ItemMeta meta = item.getItemMeta();

            displayName(meta, p, name, slot);
            lore(meta, p, name, slot);
            enchants(meta, p, name, slot);
            flags(meta, p, name, slot);
            events(meta, p, name, slot);

            item.setItemMeta(meta);

            if (Gui.getGuiConfig().isInt("Menus." + name + ".items." + slot + ".slot")) {
                inv.setItem(Gui.getGuiConfig().getInt("Menus." + name + ".items." + slot + ".slot"), item);
            }

            if (Gui.getGuiConfig().isString("Menus." + name + ".items." + slot + ".slot")) {
                String[] list = Objects.requireNonNull(Gui.getGuiConfig().getString("Menus." + name + ".items." + slot + ".slot")).split("-");
                if (list.length == 0) {
                    inv.setItem(Integer.parseInt(Objects.requireNonNull(Gui.getGuiConfig().getString("Menus." + name + ".items." + slot + ".slot"))), item);
                }
                else {
                    String from = list[0];
                    String too = list[1];
                    for (int i = Integer.parseInt(from); i <= Integer.parseInt(too); i++) {
                        inv.setItem(i, item);
                    }
                }
            }

            if (Gui.getGuiConfig().isList("Menus." + name + ".items." + slot + ".slot")) {
                for (String integer : Gui.getGuiConfig().getStringList("Menus." + name + ".items." + slot + ".slot")) {
                    String[] list = integer.split("-");
                    if (list.length == 0) {
                        inv.setItem(Integer.parseInt(integer), item);
                    } else {
                        String from = list[0];
                        String too = list[1];
                        for (int i = Integer.parseInt(from); i <= Integer.parseInt(too); i++) {
                            inv.setItem(i, item);
                        }
                    }
                }
            }

        } catch (Exception e) {
            Messages.logger("&4An item failed in " + p.getName() + "'s GUI");
        }
    }

    /**
     * This sets the display name of an item
     * @param meta Gets the item's meta
     * @param p Gets the player
     * @param name Gets the name of the Inventory
     * @param itemName Gets the name of the current item
     */

    public static void displayName(ItemMeta meta, Player p, String name, String itemName) {
        if (Gui.getGuiConfig().contains("Menus." + name + ".items." + itemName + ".display-name")) {
            meta.setDisplayName(Format.placeholder(p, Gui.getGuiConfig().getString("Menus." + name + ".items." + itemName + ".display-name")));
        }
    }

    /**
     * This sets the lore of an item
     * @param meta Gets the item's meta
     * @param p Gets the player
     * @param name Gets the name of the inventory
     * @param itemName Gets the name of the current item
     */

    public static void lore(ItemMeta meta, Player p, String name, String itemName) {
        if (Gui.getGuiConfig().contains("Menus." + name + ".items." + itemName + ".lore")) {
            List<String> lore = new ArrayList<>();
            Gui.getGuiConfig().getStringList("Menus." + name + ".items." + itemName + ".lore").forEach(line -> lore.add(Format.placeholder(p, line)));
            meta.setLore(lore);
        }
    }

    /**
     * Adds enchants to the item
     * @param meta Gets the item's meta
     * @param p Gets the player
     * @param name Gets the name of the inventory
     * @param itemName Gets the name of the current item
     */

    public static void enchants(ItemMeta meta, Player p, String name, String itemName) {
        if (Gui.getGuiConfig().contains("Menus." + name + ".items." + itemName + ".enchants")) {
            Gui.getGuiConfig().getStringList("Menus." + name + ".items." + itemName + ".enchants").forEach(enchant -> {
                try {
                    String[] split = enchant.split(":");
                    meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(split[0]))), Integer.parseInt(split[1]), true);
                } catch (NullPointerException e) {
                    Messages.logger("&4Invalid Enchant in " + p.getName() + "'s open GUI");
                }
            });
        }
    }

    /**
     * Sets the items flag
     * @param meta Gets the item's meta
     * @param p Gets the player
     * @param name Gets the name of the inventory
     * @param itemName Gets the name of the current item
     */

    public static void flags(ItemMeta meta, Player p, String name, String itemName) {
        if (Gui.getGuiConfig().contains("Menus." + name + ".items." + itemName + ".flags")) {
            Gui.getGuiConfig().getStringList("Menus." + name + ".items." + itemName + ".flags").forEach(flag -> {
                try {
                    meta.addItemFlags(ItemFlag.valueOf(flag));
                } catch (NullPointerException e) {
                    Messages.logger("&4Invalid flag in " + p.getName() + "'s open GUI");
                }
            });
        }
    }

    public static void events(ItemMeta meta, Player p, String name, String itemName) {
        if (Gui.getGuiConfig().contains("Menus." + name + ".items." + itemName + ".events")) {
            try {
                meta.setCustomModelData(Gui.getGuiConfig().getInt("Menus." + name + ".items." + itemName + ".slot"));
            } catch (NullPointerException e) {
                Messages.logger("&4Failed to initialise event in " + p.getName() + " 's open GUI");
            }
        }
    }

}
