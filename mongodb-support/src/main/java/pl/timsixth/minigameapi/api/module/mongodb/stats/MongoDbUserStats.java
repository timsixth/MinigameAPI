package pl.timsixth.minigameapi.api.module.mongodb.stats;

import pl.timsixth.minigameapi.api.module.mongodb.core.MongoDbModel;
import pl.timsixth.minigameapi.api.stats.model.AbstractUserStats;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.UUID;

public class MongoDbUserStats extends AbstractUserStats implements MongoDbModel {

    public static final String COLLECTION_NAME = "users_stats";

    public MongoDbUserStats(UUID uuid, String name, String arenaName, int wins, int defeats) {
        super(uuid, name, arenaName, wins, defeats);
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Dao getDao() {
        return new MongoDbUserStatsDao(this);
    }
}
