package pl.timsixth.minigameapi.api.module.sql.stats.factory;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.logging.MiniGameLogger;
import pl.timsixth.minigameapi.api.module.sql.stats.SQLDatabaseUserStats;
import pl.timsixth.minigameapi.api.stats.factory.UserStatsFactory;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.UUID;

public class SQLDatabaseUserStatsFactory implements UserStatsFactory {
    @Override
    public UserStats createUserStats(UUID uuid, String name, String arenaName, int wins, int defeats) {
        if (!MiniGame.getInstance().getPluginConfiguration().isUseDefaultStatsSystem()) {
            MiniGameLogger.info("Default stats system is disabled");
            return null;
        }

        return new SQLDatabaseUserStats(uuid, name, arenaName, wins, defeats);
    }

    @Override
    public UserStats createUserStats(UUID uuid, String name, String arenaName) {
        return this.createUserStats(uuid, name, arenaName, 0, 0);
    }
}
