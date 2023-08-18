package pl.timsixth.minigameapi.api.stats.manager;

import pl.timsixth.minigameapi.api.stats.AbstractStatistics;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.UUID;

/**
 * Template method of {@link UserStatsManager}
 * @param <T> every class which implemented {@link UserStats}
 */
public abstract class AbstractUserStatsManager<T extends UserStats> extends AbstractStatistics<T> implements UserStatsManager<T> {

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
