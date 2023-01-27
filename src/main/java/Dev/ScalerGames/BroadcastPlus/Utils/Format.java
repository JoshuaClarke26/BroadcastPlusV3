package Dev.ScalerGames.BroadcastPlus.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {

    public static String color(String msg) {
        if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18")
                || Bukkit.getVersion().contains("1.19")) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher match = pattern.matcher(msg);
            while (match.find()) {
                String color = msg.substring(match.start(), match.end());
                msg = msg.replace(color, ChatColor.of(color) + "");
                match = pattern.matcher(msg);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String placeholder(Player p, String msg) {

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

            msg = PlaceholderAPI.setPlaceholders(p, msg);

        }

        if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18")
                || Bukkit.getVersion().contains("1.19")) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher match = pattern.matcher(msg);
            while (match.find()) {
                String color = msg.substring(match.start(), match.end());
                msg = msg.replace(color, ChatColor.of(color) + "");
                match = pattern.matcher(msg);
            }
        }

        msg = msg.replace(":heart:", "♥").replace(":tick:", "✔").replace(":cross:", "✖")
                .replace(":warn:", "⚠").replace(":smile:", "☺").replace(":happy:", "☻").replace(":sad:", "☹").replace(":tick-box:", "☑")
                .replace(":cross-box:", "☒").replace(":star:", "⭐").replace(":sword:", "⚔").replace(":pickaxe:", "⛏").replace(":axe:", "🪓")
                .replace(":bow:", "🏹").replace(":skull:", "☠").replace(":lightning:", "⚡").replace(":sun:", "☼").replace(":moon:", "☾")
                .replace("\\n", "\n").replace(":>>:", "≫").replace("<<", "≪");


        if (msg.contains("<center>")) {
            msg = msg.replace("<center>", "");
            msg = StringUtils.center(msg, 52-msg.length() / 2);
        }

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
