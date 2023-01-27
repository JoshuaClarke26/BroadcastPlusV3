package Dev.ScalerGames.BroadcastPlus.Methods;

import Dev.ScalerGames.BroadcastPlus.Main;
import Dev.ScalerGames.BroadcastPlus.Utils.Format;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class BroadcastMethods {

    public static void sendActionBar(Player p, String msg, Integer seconds) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            int time = 0;
            @Override
            public void run() {
                if (time == seconds) {
                    Bukkit.getScheduler().cancelTask(0);
                } else {
                    time++;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Format.placeholder(p, msg)));
                }
            }
        }, 0, 20);
    }

    public static void sendTitle(Player p, String msg) {
        if (msg.contains("<sub>")) {
            String[] title = msg.split(Pattern.quote("<sub>"));
            p.sendTitle(Format.placeholder(p, title[0]), Format.placeholder(p, title[1]),
                    Main.getInstance().getConfig().getInt("TitleFadeIn"),
                    Main.getInstance().getConfig().getInt("TitleDisplayTime"),
                    Main.getInstance().getConfig().getInt("TitleFadeOut"));
        } else {
            p.sendTitle(Format.placeholder(p, msg), null,
                    Main.getInstance().getConfig().getInt("TitleFadeIn"),
                    Main.getInstance().getConfig().getInt("TitleDisplayTime"),
                    Main.getInstance().getConfig().getInt("TitleFadeOut"));
        }
    }

}
