package pl.timsixth.minigameapi.stats.manager;

import pl.timsixth.minigameapi.stats.Statistics;
import pl.timsixth.minigameapi.stats.model.UserStats;

import java.util.UUID;

/**
 * Manages default stats
 *
 * @param <T> every class which implemented {@link pl.timsixth.minigameapi.stats.model.UserStats}
 */
public interface UserStatsManager<T extends UserStats> extends Statistics<T> {
    /**
     * @param uuid user's uuid
     * @return total amount of wins
     */
    int getTotalWins(UUID uuid);

    /**
     * @param uuid user's uuid
     * @return total amount of defeats
     */
    int getTotalDefeats(UUID uuid);

    /**
     * @param uuid user's uuid
     * @return total amount of games played
     */
    int getTotalGamesPlayed(UUID uuid);

    /**
     * Adds new user
     *
     * @param type user to add
     */
    void addNewUser(T type);
}
