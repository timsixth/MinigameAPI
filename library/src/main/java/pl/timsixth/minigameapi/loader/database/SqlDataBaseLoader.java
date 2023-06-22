package pl.timsixth.minigameapi.loader.database;

import pl.timsixth.minigameapi.database.DbModel;
import pl.timsixth.minigameapi.loader.Loader;

/**
 * Loader which loads data from database
 *
 * @param <T> every class which implemented {@link DbModel}
 *
 * @see pl.timsixth.minigameapi.loader.Loader
 * @see pl.timsixth.minigameapi.loader.Loaders
 */
public interface SqlDataBaseLoader<T extends DbModel> extends Loader<T> {
    /**
     * Loads data from database table
     *
     * @param tableName table name
     */
    void load(String tableName);
}
