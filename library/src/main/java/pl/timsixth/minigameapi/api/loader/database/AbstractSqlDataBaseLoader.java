package pl.timsixth.minigameapi.api.loader.database;

import pl.timsixth.minigameapi.api.database.DbModel;
import pl.timsixth.minigameapi.api.loader.AbstractLoader;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.Loaders;

/**
 * Template method for {@link SqlDataBaseLoader}
 *
 * @param <T> every class which implemented {@link DbModel}
 * @see Loader
 * @see Loaders
 */
public abstract class AbstractSqlDataBaseLoader<T extends DbModel> extends AbstractLoader<T> implements SqlDataBaseLoader<T> {
}
