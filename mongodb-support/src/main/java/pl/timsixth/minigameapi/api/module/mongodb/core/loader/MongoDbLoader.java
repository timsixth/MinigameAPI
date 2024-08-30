package pl.timsixth.minigameapi.api.module.mongodb.core.loader;

import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.model.Model;

public interface MongoDbLoader<T extends Model> extends Loader<T> {

    void load(String collectionName);
}
