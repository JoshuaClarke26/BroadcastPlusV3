package Dev.ScalerGames.BroadcastPlus.Methods;

import Dev.ScalerGames.BroadcastPlus.Main;
import Dev.ScalerGames.BroadcastPlus.Utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BossBar {

    private org.bukkit.boss.BossBar bar;
    private final Main plugin;
    private int taskID = -1;
    public int bossID = 1;

    public BossBar(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * This creates the boss bar to broadcast
     * @param time This time the boss bar runs for
     * @param color The color of the boss bar
     * @param style The style of the boss bar
     * @param msg The message that the boss bar will display
     */

    public void createBar (Integer time, String color, String style, String msg) {
        try {
            NamespacedKey key = new NamespacedKey(Main.getInstance(), String.valueOf(bossID));
            bar = Bukkit.createBossBar(key, Format.color(msg), BarColor.valueOf(color), BarStyle.valueOf(style));
            bossID = bossID + 1;
        } catch (Exception e) {
            bar.setColor(BarColor.valueOf(Main.getInstance().getConfig().getString("BossBar.Color")));
            bar.setStyle(BarStyle.valueOf(Main.getInstance().getConfig().getString("BossBar.Style")));
        }
        bar.setVisible(true);
        run(time);
    }

    public void deleteBar () {
        for (int i=1; i<bossID; i++) {
            NamespacedKey key = new NamespacedKey(Main.getInstance(), String.valueOf(i));
            Objects.requireNonNull(Bukkit.getBossBar(key)).removeAll();
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }

    /**
     * This adds a player to the boss bar
     * @param p The player it will add
     */

    public void addPlayer(Player p) {bar.addPlayer(p);}

    /**
     * This triggers the boss bar animation
     * @param timeAmount The amount of time the boss bar will run for
     */

    public void run(Integer timeAmount) {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            double progress = 1.0;
            final double time = 1.0/(timeAmount);

            @Override
            public void run() {
                if (progress <= 0) {
                    bar.removeAll();
                    bar.setVisible(false);
                    bar = null;
                    Bukkit.getScheduler().cancelTask(taskID);
                } else {
                    if (bar != null) {
                        bar.setProgress(progress);
                        progress = progress - time;
                    } else {
                        Bukkit.getScheduler().cancelTask(taskID);
                    }
                }
            }
        },0, 20);
    }

}
