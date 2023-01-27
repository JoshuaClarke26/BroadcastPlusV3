package Dev.ScalerGames.BroadcastPlus.Methods.Gui;

import Dev.ScalerGames.BroadcastPlus.Files.Gui;
import Dev.ScalerGames.BroadcastPlus.Utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class GuiCreator {

    public static void generate(Player p, String name) {
        Inventory inv = Bukkit.createInventory(null, Gui.getGuiConfig().getInt("Menus." + name + ".size"),
                Format.placeholder(p, Gui.getGuiConfig().getString("Menus." + name + ".name")));

        Objects.requireNonNull(Gui.getGuiConfig().getConfigurationSection("Menus." + name + ".items")).getKeys(false).forEach(slot -> ItemHandler.addItem(inv, name, slot, p));

        p.openInventory(inv);
    }

}
