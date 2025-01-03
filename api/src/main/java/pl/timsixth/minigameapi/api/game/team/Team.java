package pl.timsixth.minigameapi.api.game.team;

import org.bukkit.ChatColor;
import pl.timsixth.minigameapi.api.game.user.UserGame;

import java.util.List;

/**
 * Represents every team in game
 */
public interface Team {
    /**
     * @return team's name, must be unique
     */
    String getName();

    /**
     * @return team's display name
     */
    String getDisplayName();

    /**
     * @return team's color
     */
    ChatColor getColor();

    /**
     * Sets new team's color
     *
     * @param color new color
     */
    void setColor(ChatColor color);

    /**
     * Sets new display name
     *
     * @param displayName new display name
     */
    void setDisplayName(String displayName);

    /**
     * @return users in team
     */
    List<UserGame> getUsers();

    /**
     * Adds user to team
     *
     * @param userGame user to add
     */
    void addUser(UserGame userGame);

    /**
     * Removes user from team
     *
     * @param userGame user to remove
     */
    void removeUser(UserGame userGame);

    /**
     * @return team's color as string
     */
    String getColorAsString();

    /**
     * Sets new team's color
     *
     * @param color new color as string
     */
    void setColor(String color);
}
