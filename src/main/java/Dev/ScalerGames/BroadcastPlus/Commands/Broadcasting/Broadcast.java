package Dev.ScalerGames.BroadcastPlus.Commands.Broadcasting;

import Dev.ScalerGames.BroadcastPlus.Commands.CommandCheck;
import Dev.ScalerGames.BroadcastPlus.Files.Gui;
import Dev.ScalerGames.BroadcastPlus.Files.Lang;
import Dev.ScalerGames.BroadcastPlus.Main;
import Dev.ScalerGames.BroadcastPlus.Methods.BroadcastMethods;
import Dev.ScalerGames.BroadcastPlus.Methods.Features;
import Dev.ScalerGames.BroadcastPlus.Methods.Gui.GuiCreator;
import Dev.ScalerGames.BroadcastPlus.Utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Broadcast implements CommandExecutor, TabCompleter {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("broadcast") || label.equalsIgnoreCase("announce")) {
            if (CommandCheck.execute(s, "bp.broadcast", false)) {
                if (args.length >= 2) {

                    if (args[0].equalsIgnoreCase("chat")) {
                        Bukkit.getOnlinePlayers().forEach(p -> Features.broadcastChat(Messages.broadcastMSG(args, 1), p));
                    }

                    else if (args[0].equalsIgnoreCase("bar")) {
                        if (args.length >= 3) {
                            if (CommandCheck.isInt(args[1])) {
                                Bukkit.getOnlinePlayers().forEach(player -> BroadcastMethods.sendActionBar(player, Messages.stringJoin(args, 2), Integer.parseInt(args[1])));
                            } else {
                                Messages.prefix(s, "&cInvalid Timing");
                            }
                        } else {
                            Messages.prefix(s, Lang.getLangConfig().getString("broadcast-bar-usage"));
                        }
                    }

                    else if (args[0].equalsIgnoreCase("title")) {
                        Bukkit.getOnlinePlayers().forEach(player -> BroadcastMethods.sendTitle(player, Messages.stringJoin(args, 1)));
                    }

                    else if (args[0].equalsIgnoreCase("gui")) {
                        if (args.length == 2 && Objects.requireNonNull(Gui.getGuiConfig().getConfigurationSection("Menus")).getKeys(false).contains(args[1])) {
                            Bukkit.getOnlinePlayers().forEach(player -> GuiCreator.generate(player, args[1]));
                        } else {
                            Messages.prefix(s, "&cInvalid GUI Name");
                        }
                    }

                    else if (args[0].equalsIgnoreCase("boss")) {
                        if (args.length == 2) {
                            if (Main.getInstance().getConfig().contains("Presets." + args[1] + ".boss")) {
                                Main.bar.createBar(Main.getInstance().getConfig().getInt("Presets." + args[1] + ".boss.time"),
                                        Main.getInstance().getConfig().getString("Presets." + args[1] + ".boss.color"),
                                        Main.getInstance().getConfig().getString("Presets." + args[1] + ".boss.style"),
                                        Main.getInstance().getConfig().getString("Presets." + args[1] + ".boss.text"));
                                Bukkit.getOnlinePlayers().forEach(player -> Main.bar.addPlayer(player));
                            } else {
                                Messages.prefix(s, "&cInvalid Preset Name");
                            }
                        }
                        else if (args.length >= 5) {
                            if (CommandCheck.isInt(args[1])) {
                                Main.bar.createBar(Integer.parseInt(args[1]), args[2], args[3], Messages.stringJoin(args, 4));
                                Bukkit.getOnlinePlayers().forEach(player -> Main.bar.addPlayer(player));
                            } else {
                                Messages.prefix(s, "&cInvalid Timing");
                            }
                        } else {
                            Messages.prefix(s, Lang.getLangConfig().getString("broadcast-boss-usage"));
                        }
                    }

                } else {
                    Messages.prefix(s, Lang.getLangConfig().getString("broadcast-usage"));
                }
            }
        }
        return false;
    }

    List<String> method = Arrays.asList("chat", "title", "bar", "gui", "boss");
    List<String> colors = Arrays.asList("BLUE", "GREEN", "PINK", "PURPLE", "RED", "WHITE", "YELLOW");
    List<String> styles = Arrays.asList("SOLID", "SEGMENTED_6", "SEGMENTED_10", "SEGMENTED_12", "SEGMENTED_20");

    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 1) {
            List<String> result = new ArrayList<>();
            method.forEach(a -> {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            });
            return result;
        }

        if (args[0].equalsIgnoreCase("boss")) {
            List<String> cResult = new ArrayList<>();
            if (args.length == 3) {
                colors.forEach(color -> {
                    if (color.toLowerCase().startsWith(args[2].toLowerCase()))
                        cResult.add(color);
                });
                return cResult;
            }

            List<String> sResult = new ArrayList<>();
            if (args.length == 4) {
                styles.forEach(style -> {
                    if (style.toLowerCase().startsWith(args[3].toLowerCase()))
                        sResult.add(style);
                });
                return sResult;
            }

        }

        return null;
    }

}
