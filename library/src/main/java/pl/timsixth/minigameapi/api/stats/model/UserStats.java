package pl.timsixth.minigameapi.api.stats.model;

import pl.timsixth.minigameapi.api.user.User;

/**
 * Represents every user stat for single arena
 */
public interface UserStats extends User {
    /**
     * @return user's name
     */
    String getName();

    /**
     * One record in database have data about one arena
     *
     * @return arena name
     */
    String getArenaName();

    /**
     * @return wins for one arena
     */
    int getWins();

    /**
     * @return defeats for one arena
     */
    int getDefeats();

    /**
     * @return games played for one arena
     */
    int getGamesPlayed();

    /**
     * Adds one win to one arena
     */
    void addWin();

    /**
     * Adds one defeat to one arena
     */
    void addDefeat();

}
