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

public class PermBroadcast implements CommandExecutor, TabCompleter {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("permbroadcast") || label.equalsIgnoreCase("pb")) {
            if (CommandCheck.execute(s, "bp.permbroadcast", false)) {
                if (args.length >= 3) {

                    if (args[0].equalsIgnoreCase("chat")) {
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if (player.hasPermission(args[1])) {
                                Features.broadcastChat(Messages.broadcastMSG(args, 2), player);
                            }
                        });
                    }

                    else if (args[0].equalsIgnoreCase("bar")) {
                        if (args.length >= 4) {
                            if (CommandCheck.isInt(args[2])) {
                                Bukkit.getOnlinePlayers().forEach(player -> {
                                    if (player.hasPermission(args[1])) {
                                        BroadcastMethods.sendActionBar(player, Messages.stringJoin(args, 3), Integer.parseInt(args[3]));
                                    }
                                });
                            } else {
                                Messages.prefix(s, "&cInvalid Timing");
                            }
                        } else {
                            Messages.prefix(s, Lang.getLangConfig().getString("perm-broadcast-bar-usage"));
                        }
                    }

                    else if (args[0].equalsIgnoreCase("title")) {
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if (player.hasPermission(args[1])) {
                                BroadcastMethods.sendTitle(player, Messages.stringJoin(args, 2));
                            }
                        });
                    }

                    else if (args[0].equalsIgnoreCase("gui")) {
                        if (args.length == 3 && Objects.requireNonNull(Gui.getGuiConfig().getConfigurationSection("Menus")).getKeys(false).contains(args[2])) {
                            Bukkit.getOnlinePlayers().forEach(player -> {
                                if (player.hasPermission(args[1])) {
                                    GuiCreator.generate(player, args[2]);
                                }
                            });
                        } else {
                            Messages.prefix(s, "&cInvalid GUI Name");
                        }
                    }

                    else if (args[0].equalsIgnoreCase("boss")) {
                        if (args.length == 3) {
                            if (Main.getInstance().getConfig().contains("Presets." + args[2] + ".boss")) {
                                Main.bar.createBar(Main.getInstance().getConfig().getInt("Presets." + args[2] + ".boss.time"),
                                        Main.getInstance().getConfig().getString("Presets." + args[2] + ".boss.color"),
                                        Main.getInstance().getConfig().getString("Presets." + args[2] + ".boss.style"),
                                        Main.getInstance().getConfig().getString("Presets." + args[2] + ".boss.text"));
                                Bukkit.getOnlinePlayers().forEach(player -> {
                                    if (player.hasPermission(args[1])) {
                                        Main.bar.addPlayer(player);
                                    }
                                });
                            } else {
                                Messages.prefix(s, "&cInvalid Preset Name");
                            }
                        }
                         else if (args.length >= 6 && CommandCheck.isInt(args[2])) {
                            Main.bar.createBar(Integer.parseInt(args[2]), args[3], args[4], Messages.stringJoin(args, 5));
                            Bukkit.getOnlinePlayers().forEach(player -> {
                                if (player.hasPermission(args[1])) {
                                    Main.bar.addPlayer(player);
                                }
                            });
                        } else {
                            Messages.prefix(s, Lang.getLangConfig().getString("perm-broadcast-boss-usage"));
                        }
                    }

                    else {
                        Messages.prefix(s, Lang.getLangConfig().getString("broadcast-options"));
                    }

                } else {
                    Messages.prefix(s, Lang.getLangConfig().getString("perm-broadcast-usage"));
                }
            }
        }
        return false;
    }

    List<String> method = Arrays.asList("chat", "title", "bar", "gui", "boss");
    List<String> colors = Arrays.asList("BLUE", "GREEN", "PINK", "PURPLE", "RED", "WHITE", "YELLOW");
    List<String> styles = Arrays.asList("SOLID", "SEGMENTED_6", "SEGMENTED_10", "SEGMENTED_12", "SEGMENTED_20");

    public List<String> onTabComplete (@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {

        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            method.forEach(m -> {
                if (m.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(m);
            });
            return result;
        }

        if (args[0].equalsIgnoreCase("boss")) {
            List<String> cResult = new ArrayList<>();
            if (args.length == 4) {
                colors.forEach(c -> {
                    if (c.toLowerCase().startsWith(args[3].toLowerCase()))
                        cResult.add(c);
                });
                return cResult;
            }

            List<String> sResult = new ArrayList<>();
            if (args.length == 5) {
                styles.forEach(style -> {
                    if (style.toLowerCase().startsWith(args[4].toLowerCase()))
                        sResult.add(style);
                });
                return sResult;
            }

        }

        return null;
    }

}
