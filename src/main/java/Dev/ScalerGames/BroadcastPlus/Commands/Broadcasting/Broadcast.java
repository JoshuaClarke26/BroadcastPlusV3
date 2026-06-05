package Dev.ScalerGames.BroadcastPlus.Commands.Broadcasting;

import Dev.ScalerGames.BroadcastPlus.Commands.CommandCheck;
import Dev.ScalerGames.BroadcastPlus.Files.Gui;
import Dev.ScalerGames.BroadcastPlus.Files.Lang;
import Dev.ScalerGames.BroadcastPlus.Main;
import Dev.ScalerGames.BroadcastPlus.Methods.Advancement;
import Dev.ScalerGames.BroadcastPlus.Methods.BroadcastMethods;
import Dev.ScalerGames.BroadcastPlus.Methods.Features;
import Dev.ScalerGames.BroadcastPlus.Methods.Gui.GuiCreator;
import Dev.ScalerGames.BroadcastPlus.Redis.RedisMessage;
import Dev.ScalerGames.BroadcastPlus.Utils.Format;
import Dev.ScalerGames.BroadcastPlus.Utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
                        String chatMsg = Messages.broadcastMSG(args, 1);
                        Bukkit.getOnlinePlayers().forEach(p -> Features.broadcastChat(chatMsg, p));
                        redisPublish("chat", chatMsg, "");
                    }

                    else if (args[0].equalsIgnoreCase("bar")) {
                        if (args.length >= 3) {
                            if (CommandCheck.isInt(args[1])) {
                                String barMsg = Messages.stringJoin(args, 2);
                                int timing = Integer.parseInt(args[1]);
                                Bukkit.getOnlinePlayers().forEach(player -> BroadcastMethods.sendActionBar(player, barMsg, timing));
                                redisPublish("bar", barMsg, String.valueOf(timing));
                            } else {
                                Messages.prefix(s, "&cInvalid Timing");
                            }
                        } else {
                            Messages.prefix(s, Lang.getLangConfig().getString("broadcast-bar-usage"));
                        }
                    }

                    else if (args[0].equalsIgnoreCase("title")) {
                        String titleMsg = Messages.stringJoin(args, 1);
                        Bukkit.getOnlinePlayers().forEach(player -> BroadcastMethods.sendTitle(player, titleMsg));
                        redisPublish("title", titleMsg, "");
                    }

                    else if (args[0].equalsIgnoreCase("gui")) {
                        if (args.length == 2 && Objects.requireNonNull(Gui.getGuiConfig().getConfigurationSection("Menus")).getKeys(false).contains(args[1])) {
                            Bukkit.getOnlinePlayers().forEach(player -> GuiCreator.generate(player, args[1]));
                            // GUI is not synchronized via Redis (it depends on local state).
                        } else {
                            Messages.prefix(s, "&cInvalid GUI Name");
                        }
                    }

                    else if (args[0].equalsIgnoreCase("boss")) {
                        if (args.length == 2) {
                            if (Main.getInstance().getConfig().contains("Presets." + args[1] + ".boss")) {
                                int t = Main.getInstance().getConfig().getInt("Presets." + args[1] + ".boss.time");
                                String c = Main.getInstance().getConfig().getString("Presets." + args[1] + ".boss.color");
                                String st = Main.getInstance().getConfig().getString("Presets." + args[1] + ".boss.style");
                                String tx = Main.getInstance().getConfig().getString("Presets." + args[1] + ".boss.text");
                                Main.bar.createBar(t, c, st, tx);
                                Bukkit.getOnlinePlayers().forEach(player -> Main.bar.addPlayer(player));
                                redisPublish("boss", tx, t + "|" + c + "|" + st);
                            } else {
                                Messages.prefix(s, "&cInvalid Preset Name");
                            }
                        }
                        else if (args.length >= 5) {
                            if (CommandCheck.isInt(args[1])) {
                                String bossMsg = Messages.stringJoin(args, 4);
                                Main.bar.createBar(Integer.parseInt(args[1]), args[2], args[3], bossMsg);
                                Bukkit.getOnlinePlayers().forEach(player -> Main.bar.addPlayer(player));
                                redisPublish("boss", bossMsg, args[1] + "|" + args[2] + "|" + args[3]);
                            } else {
                                Messages.prefix(s, "&cInvalid Timing");
                            }
                        } else {
                            Messages.prefix(s, Lang.getLangConfig().getString("broadcast-boss-usage"));
                        }
                    }

                    else if (args[0].equalsIgnoreCase("advancement")) {
                        if (args.length >= 3) {
                            if (Material.matchMaterial(args[1]) != null) {
                                String advMsg = Messages.stringJoin(args, 3);
                                Bukkit.getOnlinePlayers().forEach(player -> Advancement.display(player, args[1].toLowerCase(), Advancement.Style.valueOf(args[2]), Format.placeholder(player, advMsg)));
                                redisPublish("advancement", advMsg, args[1].toLowerCase() + "|" + args[2]);
                            } else {
                                Messages.prefix(s, "&cInvalid Item");
                            }
                        } else {
                            Messages.prefix(s, Lang.getLangConfig().getString("broadcast-advancement-usage"));
                        }
                    }

                } else {
                    Messages.prefix(s, Lang.getLangConfig().getString("broadcast-usage"));
                }
            }
        }
        return false;
    }

    List<String> method = Arrays.asList("chat", "title", "bar", "gui", "boss", "advancement");
    List<String> colors = Arrays.asList("BLUE", "GREEN", "PINK", "PURPLE", "RED", "WHITE", "YELLOW");
    List<String> styles = Arrays.asList("SOLID", "SEGMENTED_6", "SEGMENTED_10", "SEGMENTED_12", "SEGMENTED_20");
    List<String> advanceStyles = Arrays.asList("GOAL", "TASK", "CHALLENGE");

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

        if (args[0].equalsIgnoreCase("advancement")) {
           List<String> sResult = new ArrayList<>();
           if (args.length == 3) {
               advanceStyles.forEach(style -> {
                   if (style.toLowerCase().startsWith(args[2].toLowerCase()))
                       sResult.add(style);
               });
               return sResult;
           }
        }

        return null;
    }

    private void redisPublish(String type, String message, String extra) {
        if (Main.redis != null && Main.redis.isEnabled()) {
            String server = Main.getInstance().getConfig().getString("Redis.server-name", "default");
            Main.redis.publish(new RedisMessage(type, message, extra, server));
        }
    }

    private String getItemKey(Material material) {
        if(material.isBlock()){
            String id = material.getKey().getKey();

            return "block.minecraft."+id;
        } else if(material.isItem()){
            String id = material.getKey().getKey();

            return "item.minecraft."+id;
        }
        return "block.minecraft.dirt";
    }

}
