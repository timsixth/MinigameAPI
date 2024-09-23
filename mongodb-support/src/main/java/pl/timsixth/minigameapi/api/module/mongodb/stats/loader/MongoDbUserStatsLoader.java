package pl.timsixth.minigameapi.api.module.mongodb.stats.loader;

import org.bson.Document;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.module.mongodb.core.loader.AbstractMongoDbLoader;
import pl.timsixth.minigameapi.api.module.mongodb.stats.MongoDbUserStats;
import pl.timsixth.minigameapi.api.stats.loader.UserStatsLoader;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.UUID;

public class MongoDbUserStatsLoader extends AbstractMongoDbLoader<UserStats> implements UserStatsLoader {
    @Override
    protected String getCollectionName() {
        return MongoDbUserStats.COLLECTION_NAME;
    }

    @Override
    protected void loadDocument(Document document) {
        UserStats userStats = MiniGame.getUserStatsFactory().createUserStats(
                UUID.fromString(document.getString("uuid")),
                document.getString("name"),
                document.getString("arenaName"),
                document.getInteger("wins"),
                document.getInteger("defeats")
        );

        addObject(userStats);
    }
}
