package pl.timsixth.minigameapi.api.module.mongodb.coins.loader;

import org.bson.Document;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.api.module.mongodb.coins.MongoDbUserCoins;
import pl.timsixth.minigameapi.api.module.mongodb.core.loader.AbstractMongoDbLoader;

import java.util.UUID;

public class MongoDbUserCoinsLoader extends AbstractMongoDbLoader<UserCoins> implements UserCoinsLoader {

    @Override
    protected void loadDocument(Document document) {
        UserCoins userCoins = MiniGame.getUserCoinsFactory().createUserCoins(
                UUID.fromString(document.getString("uuid")),
                document.getDouble("coins")
        );

        addObject(userCoins);
    }

    @Override
    protected String getCollectionName() {
        return MongoDbUserCoins.COLLECTION_NAME;
    }
}
