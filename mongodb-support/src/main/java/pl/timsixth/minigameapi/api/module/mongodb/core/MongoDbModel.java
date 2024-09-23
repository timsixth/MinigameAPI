package pl.timsixth.minigameapi.api.module.mongodb.core;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.model.ManageableModel;

public interface MongoDbModel extends ManageableModel {

    String getCollectionName();

    default String getCollectionNameWithPrefix() {
        String collectionName = getCollectionName();

        if (collectionName.isEmpty()) {
            throw new IllegalStateException("Cannot get collection name, because collection name is empty.");
        }

        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + collectionName;
    }
}
