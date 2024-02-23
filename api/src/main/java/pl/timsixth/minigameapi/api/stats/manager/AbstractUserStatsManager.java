package pl.timsixth.minigameapi.api.stats.manager;

import pl.timsixth.minigameapi.api.stats.AbstractStatistics;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.UUID;

/**
 * Template method of {@link UserStatsManager}
 */
public abstract class AbstractUserStatsManager extends AbstractStatistics<UserStats> implements UserStatsManager {

    @Override
    public int getTotalDefeats(UUID uuid) {
        return getTotal(uuid, UserStats::getDefeats);
    }

    @Override
    public int getTotalGamesPlayed(UUID uuid) {
        return getTotal(uuid, UserStats::getGamesPlayed);
    }

    @Override
    public int getTotalWins(UUID uuid) {
        return getTotal(uuid, UserStats::getWins);
    }
}
