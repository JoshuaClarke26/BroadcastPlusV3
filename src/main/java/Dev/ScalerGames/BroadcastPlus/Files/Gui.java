package Dev.ScalerGames.BroadcastPlus.Files;

import Dev.ScalerGames.BroadcastPlus.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Gui {

    private static FileConfiguration guiConfig = null;
    private static File guiFile = null;

    public static void reloadGui() {
        if (guiFile == null)
            guiFile = new File(Main.getInstance().getDataFolder(), "gui.yml");

        guiConfig = YamlConfiguration.loadConfiguration(guiFile);

        InputStream defaultStream = Main.getInstance().getResource("gui.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            guiConfig.setDefaults(defaultConfig);
        }
    }

    public static FileConfiguration getGuiConfig() {
        if (guiConfig == null)
            reloadGui();

        return guiConfig;
    }

    public static void saveGui() {
        if (guiConfig == null || guiFile == null)
            return;

        try {
            getGuiConfig().save(guiFile);
        } catch (IOException ex) {
            Main.getInstance().getLogger().warning("Could not save config to " + guiFile);
        }
    }

    public static void saveDefaultGui() {
        if (guiFile == null)
            guiFile = new File(Main.getInstance().getDataFolder(), "gui.yml");

        if (!guiFile.exists()) {
            Main.getInstance().saveResource("gui.yml", false);
        }
    }

}
