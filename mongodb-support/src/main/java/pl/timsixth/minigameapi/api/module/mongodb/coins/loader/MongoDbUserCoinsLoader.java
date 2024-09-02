package pl.timsixth.minigameapi.api.module.mongodb.coins.loader;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.api.module.mongodb.coins.MongoDbUserCoins;
import pl.timsixth.minigameapi.api.module.mongodb.core.loader.AbstractMongoDbLoader;

import java.util.UUID;

public class MongoDbUserCoinsLoader extends AbstractMongoDbLoader<UserCoins> implements UserCoinsLoader {

    @Override
    public void load(String collectionName) {
        MongoDatabase mongoDatabase = getMongoDatabase();

            MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

            try (MongoCursor<Document> cursor = collection.find().cursor()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();

                    UserCoins userCoins = MiniGame.getUserCoinsFactory().createUserCoins(
                            UUID.fromString(document.getString("uuid")),
                            document.getDouble("coins")
                    );

                    addObject(userCoins);
                }
            }
    }

    @Override
    protected String getCollectionName() {
        return MongoDbUserCoins.COLLECTION_NAME;
    }
}
