package Dev.ScalerGames.BroadcastPlus.Commands;

import Dev.ScalerGames.BroadcastPlus.Files.Lang;
import Dev.ScalerGames.BroadcastPlus.Utils.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCheck {

    public static boolean execute(CommandSender s, String perm, boolean player_only) {
        if (player_only) {
            if (s instanceof Player) {
                if (s.hasPermission(perm)) {
                    return true;
                } else {
                    Messages.prefix(s, Lang.getLangConfig().getString("broadcast-permission"));
                }
            } else {
                Messages.prefix(s, Lang.getLangConfig().getString("player-only-command"));
            }
        } else {
            if (s.hasPermission(perm)) {
                return true;
            } else {
                Messages.prefix(s, Lang.getLangConfig().getString("broadcast-permission"));
            }
        }
        return false;
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
