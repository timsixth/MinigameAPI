package pl.timsixth.minigameapi.api.loader.database;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.database.DbModel;
import pl.timsixth.minigameapi.api.loader.AbstractLoader;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.model.Model;

/**
 * Template method for {@link SqlDataBaseLoader}
 *
 * @param <T> every class which implemented {@link DbModel}
 * @see Loader
 * @see Loaders
 */
public abstract class AbstractSqlDataBaseLoader<T extends Model> extends AbstractLoader<T> implements SqlDataBaseLoader<T> {

    protected ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    @Override
    public void load() {
        load(MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + getTableName());
    }

    protected String getTableName() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
