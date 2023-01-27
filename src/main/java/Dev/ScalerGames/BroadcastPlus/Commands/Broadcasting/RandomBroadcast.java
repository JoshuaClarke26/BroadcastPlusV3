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
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class RandomBroadcast implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("randombroadcast") || label.equalsIgnoreCase("rb")) {
            if (CommandCheck.execute(s, "bp.randombroadcast", false)) {
                if (args.length >= 2) {

                    if (args[0].equalsIgnoreCase("chat")) {
                        Player rand = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
                        Features.broadcastChat(Messages.broadcastMSG(args, 1), rand);
                    }

                    else if (args[0].equalsIgnoreCase("bar")) {
                        if (args.length >= 3 && CommandCheck.isInt(args[1])) {
                            Player rand = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
                            BroadcastMethods.sendActionBar(rand, Messages.stringJoin(args, 2), Integer.parseInt(args[1]));
                        } else {
                            Messages.prefix(s, Lang.getLangConfig().getString("random-broadcast-bar-usage"));
                        }
                    }

                    else if (args[0].equalsIgnoreCase("title")) {
                        Player rand = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
                        BroadcastMethods.sendTitle(rand, Messages.stringJoin(args, 1));
                    }

                    else if (args[0].equalsIgnoreCase("gui")) {
                        if (args.length == 2 && Objects.requireNonNull(Gui.getGuiConfig().getConfigurationSection("Menus")).getKeys(false).contains(args[1])) {
                            Player rand = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
                            GuiCreator.generate(rand, args[1]);
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
                                Player rand = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
                                Main.bar.addPlayer(rand);
                            } else {
                                Messages.prefix(s, "&cInvalid Preset Name");
                            }
                        }
                        else if (args.length >= 5) {
                            if (CommandCheck.isInt(args[1])) {
                                Main.bar.createBar(Integer.parseInt(args[1]), args[2], args[3], Messages.stringJoin(args, 4));
                                Player rand = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
                                Main.bar.addPlayer(rand);
                            } else {
                                Messages.prefix(s, Lang.getLangConfig().getString("&cInvalid Timing"));
                            }
                        } else {
                            Messages.prefix(s, Lang.getLangConfig().getString("random-broadcast-boss-usage"));
                        }
                    }

                    else {
                        Messages.prefix(s, Lang.getLangConfig().getString("broadcast-options"));
                    }

                } else {
                    Messages.prefix(s, Lang.getLangConfig().getString("broadcast-permission"));
                }
            }
        }
        return false;
    }

}
