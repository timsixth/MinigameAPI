package pl.timsixth.minigameapi.api.stats.factory;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.database.impl.DbModelImpl;
import pl.timsixth.minigameapi.api.file.impl.SingleFileModelImpl;
import pl.timsixth.minigameapi.api.stats.model.SQLDatabaseUserStatsAdapter;
import pl.timsixth.minigameapi.api.stats.model.SingleFileUserStatsAdapter;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.UUID;

public class UserStatsFactoryImpl implements UserStatsFactory {
    @Override
    public UserStats createUserStats(UUID uuid, String name, String arenaName, int wins, int defeats) {
        if (!MiniGame.getInstance().getPluginConfiguration().isUseDefaultStatsSystem())
            throw new IllegalStateException("Default stats system is disabled");

        if (MiniGame.getInstance().getPluginConfiguration().isUseDataBase())
            return new SQLDatabaseUserStatsAdapter(new DbModelImpl(), uuid, name, arenaName, wins, defeats);

        return new SingleFileUserStatsAdapter(new SingleFileModelImpl(), uuid, name, arenaName, wins, defeats);
    }

    @Override
    public UserStats createUserStats(UUID uuid, String name, String arenaName) {
        return createUserStats(uuid, name, arenaName, 0, 0);
    }
}
