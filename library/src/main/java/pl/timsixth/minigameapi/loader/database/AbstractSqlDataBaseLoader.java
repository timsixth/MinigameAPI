package pl.timsixth.minigameapi.loader.database;

import pl.timsixth.minigameapi.database.DbModel;
import pl.timsixth.minigameapi.loader.AbstractLoader;

/**
 * Template method for {@link SqlDataBaseLoader}
 *
 * @param <T> every class which implemented {@link DbModel}
 * @see pl.timsixth.minigameapi.loader.Loader
 * @see pl.timsixth.minigameapi.loader.Loaders
 */
public abstract class AbstractSqlDataBaseLoader<T extends DbModel> extends AbstractLoader<T> implements SqlDataBaseLoader<T> {
}
