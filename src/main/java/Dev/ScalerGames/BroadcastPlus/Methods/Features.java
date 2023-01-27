package Dev.ScalerGames.BroadcastPlus.Methods;

import Dev.ScalerGames.BroadcastPlus.Utils.Format;
import Dev.ScalerGames.BroadcastPlus.Utils.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Features {

    public static String sound(String msg, Player p) {
        String finalString = msg;
        if (msg.contains("<sound>") && msg.contains("</sound>")) {
            try {
                String sound = StringUtils.substringBetween(msg, "<sound>", "</sound>");
                //p.playSound(p, Sound.valueOf(sound.toUpperCase().replace(".", "_")), 0.5F, 0.5F);
                p.playSound(p.getLocation(), Sound.valueOf(sound.toUpperCase().replace(".", "_")), 100, 1);
                finalString = msg.replace(sound, "").replace("</sound>", "").replace("<sound>", "");
            } catch (Exception e) {
                Messages.prefix(p, "&cSound failed to send");
            }
        }
        return finalString;
    }

    public static void broadcastChat(String msg, Player p) {
        String output = Format.placeholder(p, sound(msg, p));
        if ((output.contains("<hover>") && output.contains("</hover>")) || (output.contains("<url>") && output.contains("</url>")) || (output.contains("<command>") && output.contains("</command>"))) {
            TextComponent text = new TextComponent();

            if (output.contains("<hover>") && output.contains("</hover>")) {
                try {
                    String hover = StringUtils.substringBetween(output, "<hover>", "</hover>");
                    text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Format.placeholder(p, hover))));
                    output = output.replace("<hover>", "").replace("</hover>", "").replace(hover, "");
                } catch (Exception e) {
                    Messages.prefix(p, "&cHover event failed");
                }
            }

            if (output.contains("<url>") && output.contains("</url>")) {
                try {
                    String url = StringUtils.substringBetween(output, "<url>", "</url>");
                    text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
                    output = output.replace("<url>", "").replace("</url>", "").replace(url, "");
                } catch (Exception e) {
                    Messages.prefix(p, "&cUrl event failed");
                }
            }

            if (output.contains("<command>") && output.contains("</command>")) {
                try {
                    String command = StringUtils.substringBetween(output, "<command>", "</command>");
                    text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
                    output = output.replace("<command>", "").replace("</command>", "").replace(command, "");
                } catch (Exception e) {
                    Messages.prefix(p, "&cCommand event failed");
                }
            }

            text.setText(output);
            p.spigot().sendMessage(text);

        } else {
            p.sendMessage(output);
        }
    }

}
