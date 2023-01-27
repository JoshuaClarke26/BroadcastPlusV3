package Dev.ScalerGames.BroadcastPlus.Methods.Gui;

import Dev.ScalerGames.BroadcastPlus.Files.Gui;
import Dev.ScalerGames.BroadcastPlus.Utils.Format;
import Dev.ScalerGames.BroadcastPlus.Utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Objects.requireNonNull(Gui.getGuiConfig().getConfigurationSection("Menus")).getKeys(false).forEach(menu -> {
            if (event.getView().getTitle().equalsIgnoreCase(Format.placeholder((Player) event.getWhoClicked(), Gui.getGuiConfig().getString("Menus." + menu + ".name")))) {
                if (event.getCurrentItem() != null) {
                    event.setCancelled(true);
                    if (!event.getCurrentItem().hasItemMeta()) return;
                    if (!Objects.requireNonNull(event.getCurrentItem().getItemMeta()).hasCustomModelData()) return;
                    Objects.requireNonNull(Gui.getGuiConfig().getConfigurationSection("Menus." + menu + ".items")).getKeys(false).forEach(item -> {
                        if (Gui.getGuiConfig().getInt("Menus." + menu + ".items." + item + ".slot") == event.getSlot()) {
                            if (Gui.getGuiConfig().contains("Menus." + menu + ".items." + item + ".events")) {
                                try {
                                    event.getWhoClicked().closeInventory();
                                    Gui.getGuiConfig().getStringList("Menus." + menu + ".items." + item + ".events").forEach(e -> commandEvent(e, (Player) event.getWhoClicked()));
                                } catch (Exception e) {
                                    Messages.logger("&4Failed to trigger event in " + event.getWhoClicked() + "'s open inventory");
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void commandEvent(String event, Player p) {
        if (event.contains("p-cmd:")) {
            Bukkit.dispatchCommand(p, Format.placeholder(p, event.replace("p-cmd:", "")));
        }
        else if (event.contains("c-cmd")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Format.placeholder(p, event.replace("c-cmd:", "")));
        }
    }

}
