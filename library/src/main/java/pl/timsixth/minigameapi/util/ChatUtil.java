package pl.timsixth.minigameapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class ChatUtil {
    /**
     * Translates and symbol to colors in text
     *
     * @param text text to translates
     * @return translated text
     */
    public static String chatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
