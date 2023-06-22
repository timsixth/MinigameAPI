package pl.timsixth.thetag.util;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public class PlayerUtil {

    public static void sendMessage(Player player, String message) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            player.sendMessage(message);
            return;
        }

        player.sendMessage(PlaceholderAPI.setPlaceholders(player, message));
    }

}

