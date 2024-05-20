package pl.timsixth.minigameapi.api.stats.manager;

import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.stats.Statistics;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.UUID;

/**
 * Manages default stats
 */
public interface UserStatsManager extends Statistics<UserStats> {
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
     * Gets or creates new {@link UserStats}
     *
     * @param player player to create user
     * @param arena  arena to get arena name
     * @return created or got user
     */
    UserStats getUserStatsOrCreate(Player player, Arena arena);
}
