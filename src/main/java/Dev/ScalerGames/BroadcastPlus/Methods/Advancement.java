package Dev.ScalerGames.BroadcastPlus.Methods;

import Dev.ScalerGames.BroadcastPlus.Main;
import Dev.ScalerGames.BroadcastPlus.Utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class Advancement {

    private final NamespacedKey key;
    private final String icon;
    private final String message;
    private final Style style;

    private Advancement(String icon, String message, Style style) {
        this.key = new NamespacedKey(Main.getInstance(), UUID.randomUUID().toString());
        this.icon = icon;
        this.message = message;
        this.style = style;
    }

    private void start(Player player) {
        create();
        grant(player);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            remove(player);
        }, 10);
    }

    private void create() {
        String itemString = "item";
        if (Bukkit.getVersion().equalsIgnoreCase("1.20.5") || Bukkit.getVersion().equalsIgnoreCase("1.20.6")) {
            itemString = "id";
        }

        try {
            String[] version = Bukkit.getVersion().split("\\.");
            if (Integer.parseInt(version[1]) >= 21) {
                itemString = "id";
            }
        } catch (Exception ex) {
            Messages.logger("&cFailed to retrieve Bukkit version");
        }

        Bukkit.getUnsafe().loadAdvancement(key, "{\n" +
                "    \"criteria\": {\n" +
                "        \"trigger\": {\n" +
                "            \"trigger\": \"minecraft:impossible\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"display\": {\n" +
                "        \"icon\": {\n" +
                "            \"" + itemString + "\": \"minecraft:" + icon + "\"\n" +
                "        },\n" +
                "        \"title\": {\n" +
                "            \"text\": \"" + message.replace("|", "\n") + "\"\n" +
                "        },\n" +
                "        \"description\": {\n" +
                "            \"text\": \"\"\n" +
                "        },\n" +
                "        \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
                "        \"frame\": \"" + style.toString().toLowerCase() + "\",\n" +
                "        \"announce_to_chat\": false,\n" +
                "        \"show_toast\": true,\n" +
                "        \"hidden\": true\n" +
                "    },\n" +
                "    \"requirements\": [\n" +
                "        [\n" +
                "            \"trigger\"\n" +
                "        ]\n" +
                "    ]\n" +
                "}");
    }

    private void grant(Player player) {
        player.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement(key))).awardCriteria("trigger");
    }

    private void remove(Player player) {
        player.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement(key))).revokeCriteria("trigger");
    }

    public static void display(Player player, String icon, Style style, String message) {
        new Advancement(icon, message, style).start(player);
    }

    public static enum Style {
        GOAL,
        TASK,
        CHALLENGE;
    }

}
