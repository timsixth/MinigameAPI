package pl.timsixth.minigameapi.api.module.mongodb.core.loader;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.loader.AbstractLoader;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.module.mongodb.MongoDbModule;

public abstract class AbstractMongoDbLoader<T extends Model> extends AbstractLoader<T> implements MongoDbLoader<T> {

    @Override
    public void load() {
        load(getCollectionNameWithPrefix());
    }

    /**
     * Gets collection name which loader will load data
     *
     * @return collection name
     */
    protected abstract String getCollectionName();

    /**
     * Gets collection name with minigame tables' prefix
     *
     * @return collection name with minigame tables' prefix
     */
    protected String getCollectionNameWithPrefix() {
        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + getCollectionName();
    }

    protected MongoDatabase getMongoDatabase() {
        return MongoDbModule.getInstance().getMongoDbConnector().getMongoDatabase();
    }

    @Override
    public void load(String collectionName) {
        MongoDatabase mongoDatabase = getMongoDatabase();

        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

        try (MongoCursor<Document> cursor = collection.find().cursor()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                loadDocument(document);
            }
        }
    }

    protected void loadDocument(Document document) {
        throw new UnsupportedOperationException();
    }
}
