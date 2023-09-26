package pl.timsixth.minigameapi.api.loader.database;

import pl.timsixth.minigameapi.api.database.DbModel;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.model.Model;

/**
 * Loader which loads data from database
 *
 * @param <T> every class which implemented {@link DbModel}
 *
 * @see Loader
 * @see Loaders
 */
public interface SqlDataBaseLoader<T extends Model> extends Loader<T> {
    /**
     * Loads data from database table
     *
     * @param tableName table name
     */
    void load(String tableName);
}
