package pl.timsixth.minigameapi.api.module.mongodb.stats.factory;

import pl.timsixth.minigameapi.api.module.mongodb.stats.MongoDbUserStats;
import pl.timsixth.minigameapi.api.stats.factory.UserStatsFactory;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.UUID;

public class MongoDbUserStatsFactory implements UserStatsFactory {
    @Override
    public UserStats createUserStats(UUID uuid, String name, String arenaName, int wins, int defeats) {
        return new MongoDbUserStats(uuid, name, arenaName, wins, defeats);
    }

    @Override
    public UserStats createUserStats(UUID uuid, String name, String arenaName) {
        return createUserStats(uuid, name, arenaName, 0, 0);
    }
}
