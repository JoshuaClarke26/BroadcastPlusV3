package Dev.ScalerGames.BroadcastPlus.Commands;

import Dev.ScalerGames.BroadcastPlus.Files.Lang;
import Dev.ScalerGames.BroadcastPlus.Main;
import Dev.ScalerGames.BroadcastPlus.Utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoBroadcastCMD implements CommandExecutor, TabCompleter {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("autobroadcast")) {
            if (CommandCheck.execute(s, "bp.autobroadcast", true)) {

                if (args.length == 1) {

                    Player p = (Player) s;
                    if (args[0].equalsIgnoreCase("enable")) {
                        if (Main.getInstance().autoBroadcast.containsKey(p.getUniqueId())) {
                            if (Main.getInstance().autoBroadcast.get(p.getUniqueId())) {
                                Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-enabled-already"));
                            } else {
                                Main.getInstance().autoBroadcast.replace(p.getUniqueId(), true);
                                Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-enabled"));
                            }
                        } else {
                            Main.getInstance().autoBroadcast.put(p.getUniqueId(), true);
                            Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-enabled-already"));
                        }
                    }

                    else if (args[0].equalsIgnoreCase("disable")) {
                        if (Main.getInstance().autoBroadcast.containsKey(p.getUniqueId())) {
                            if (Main.getInstance().autoBroadcast.get(p.getUniqueId())) {
                                Main.plugin.autoBroadcast.replace(p.getUniqueId(), false);
                                Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-disabled"));
                            } else {
                                Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-disabled-already"));
                            }
                        } else {
                            Main.getInstance().autoBroadcast.put(p.getUniqueId(), false);
                            Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-disabled"));
                        }
                    }

                    else if (args[0].equalsIgnoreCase("toggle")) {
                        if (Main.getInstance().autoBroadcast.containsKey(p.getUniqueId()))  {
                            if (Main.getInstance().autoBroadcast.get(p.getUniqueId())) {
                                Main.getInstance().autoBroadcast.replace(p.getUniqueId(), false);
                                Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-disabled"));
                            } else {
                                Main.getInstance().autoBroadcast.replace(p.getUniqueId(), true);
                                Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-enabled"));
                            }
                        } else {
                            Main.getInstance().autoBroadcast.put(p.getUniqueId(), false);
                            Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-disabled"));
                        }
                    }

                    else {
                        Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-usage"));
                    }

                } else {
                    Messages.prefix(s, Lang.getLangConfig().getString("auto-broadcast-usage"));
                }

            }
        }

        return false;
    }

    List<String> options = Arrays.asList("enable", "disable", "toggle");
    public List<String> onTabComplete(CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (s.hasPermission("bp.autobroadcast")) {
            List<String> result = new ArrayList<>();
            if (args.length == 1) {
                options.forEach(option -> {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase()))
                        result.add(option);
                });
                return result;
            }
        }
        return null;
    }

}
