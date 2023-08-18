package pl.timsixth.minigameapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ChatUtil {
    /**
     * Translates 'and' symbol to colors in text
     *
     * @param text text to translates
     * @return translated text
     */
    public static String chatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Translates 'and' symbol to colors in strings list
     *
     * @param stringList list of string to translate
     * @return translated list of strings
     */
    public static List<String> chatColor(List<String> stringList) {
        List<String> strings = new ArrayList<>();
        for (String str : stringList) {
            String msg = ChatColor.translateAlternateColorCodes('&', str);
            strings.add(msg);
        }
        return strings;
    }
}
