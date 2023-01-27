package Dev.ScalerGames.BroadcastPlus.Methods;

import Dev.ScalerGames.BroadcastPlus.Main;
import Dev.ScalerGames.BroadcastPlus.Utils.Format;
import Dev.ScalerGames.BroadcastPlus.Utils.Messages;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.Random;

public class AutoBroadcast {

    public Main plugin;
    public AutoBroadcast(Main plugin) {
        this.plugin = plugin;
    }
    public Integer msgLine = 0;

    public void autoMessage() {
        if (Main.getInstance().getConfig().getBoolean("AutoBroadcast.enabled")) {
            Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {

                if (Main.getInstance().getConfig().contains("AutoBroadcast.method") && Objects.requireNonNull(Main.getInstance().getConfig().getString("AutoBroadcast.method")).equalsIgnoreCase("order")) {
                    String msg = Main.getInstance().getConfig().getStringList("AutoBroadcast.messages").get(msgLine);
                    Bukkit.getConsoleSender().sendMessage(Format.color(Messages.autoBroadcastMSG(msg)));
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (Main.plugin.autoBroadcast.get(player.getUniqueId()) == null || Main.plugin.autoBroadcast.get(player.getUniqueId())) {
                            player.sendMessage(Format.placeholder(player, Messages.autoBroadcastMSG(msg)));
                        }
                    });
                    if (msgLine == Main.getInstance().getConfig().getStringList("AutoBroadcast.messages").size()-1) {
                        msgLine = 0;
                    } else {
                        msgLine++;
                    }
                } else {
                    String msg = Main.getInstance().getConfig().getStringList("AutoBroadcast.messages").get(new Random().nextInt(Main.getInstance().getConfig().getStringList("AutoBroadcast.messages").size()));
                    Bukkit.getConsoleSender().sendMessage(Format.color(Messages.autoBroadcastMSG(msg)));
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if (Main.plugin.autoBroadcast.get(player.getUniqueId()) == null || Main.plugin.autoBroadcast.get(player.getUniqueId())) {
                            player.sendMessage(Format.placeholder(player, Messages.autoBroadcastMSG(msg)));
                        }
                    });
                }

            }, 0L, 20L * Main.getInstance().getConfig().getInt("AutoBroadcast.time"));
        }
    }

}
