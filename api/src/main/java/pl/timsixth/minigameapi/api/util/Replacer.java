package pl.timsixth.minigameapi.api.util;

import lombok.experimental.UtilityClass;
import pl.timsixth.minigameapi.api.MiniGame;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Addons system will be removed
 */
@UtilityClass
@Deprecated
public class Replacer {
    /**
     * Replaces '{admin_command}' and '{player_command}' in string
     *
     * @param text text to replace
     * @return replaced text
     */
    public static String replaceCommandNames(String text) {
        return text.replace("{admin_command}", MiniGame.getInstance().getAdminCommand().getName())
                .replace("{player_command}", MiniGame.getInstance().getPlayerCommand().getName());
    }

    /**
     * Replaces '{admin_command}' and '{player_command}' in string list
     *
     * @param texts list of strings to replace
     * @return replaced list of strings
     */
    public static List<String> replaceCommandNames(List<String> texts) {
        return texts.stream()
                .map(Replacer::replaceCommandNames)
                .collect(Collectors.toList());
    }
}
