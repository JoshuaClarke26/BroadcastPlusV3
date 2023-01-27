package Dev.ScalerGames.BroadcastPlus.Utils;

import Dev.ScalerGames.BroadcastPlus.Files.Lang;
import Dev.ScalerGames.BroadcastPlus.Main;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

public class Messages {

    public static void prefix(CommandSender s, String msg) {
        if (s instanceof Player) {
            s.sendMessage(Format.placeholder((Player) s, Lang.getLangConfig().getString("prefix") + msg));
        } else {
            s.sendMessage(Format.color(Lang.getLangConfig().getString("prefix") + msg));
        }
    }

    public static void logger(String log) {
        Bukkit.getConsoleSender().sendMessage("[BroadcastPlus] " + Format.color(log));
    }

    public static String stringJoin(String[] args, Integer from) {
        return StringUtils.join(Arrays.copyOfRange(args, from, args.length), " ");
    }

    public static String broadcastMSG(String[] args, Integer arg_num) {
        if (Main.getInstance().getConfig().contains("Presets." + args[arg_num] + ".message")) {
            return String.join("\n", Main.getInstance().getConfig().getStringList("Presets." + args[arg_num] + ".message"));
        }
        return Main.getInstance().getConfig().getString("BroadcastPrefix") + "&r" + stringJoin(args, arg_num);
    }

    public static String autoBroadcastMSG(String msg) {
        if (msg.contains("preset:") && Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("Presets")).getKeys(false).contains(msg.replace("preset:", ""))) {
            return String.join("\n", Main.getInstance().getConfig().getStringList("Presets." + msg.replace("preset:", "") + ".message"));
        }
        return Main.getInstance().getConfig().getString("BroadcastPrefix") + msg;
    }

}
