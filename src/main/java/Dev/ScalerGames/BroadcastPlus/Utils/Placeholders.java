package Dev.ScalerGames.BroadcastPlus.Utils;

import Dev.ScalerGames.BroadcastPlus.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {return true;}

    @Override
    public @NotNull String getAuthor() {return "ScalerGames";}

    @Override
    public @NotNull String getIdentifier() {return "broadcastplus";}

    @Override
    public @NotNull String getVersion() {return "3.0.0";}

    @Override
    public String onRequest(OfflinePlayer p, String holder) {

        if (holder.equals("prefix")) {
            return Main.getInstance().getConfig().getString("BroadcastPrefix");
        }

        return "";
    }

}
