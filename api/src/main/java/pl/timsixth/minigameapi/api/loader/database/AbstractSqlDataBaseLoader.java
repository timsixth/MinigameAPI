package pl.timsixth.minigameapi.api.loader.database;

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
@Deprecated
public abstract class AbstractSqlDataBaseLoader<T extends Model> extends AbstractLoader<T> implements SqlDataBaseLoader<T> {

    // protected ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    @Override
    public void load() {
        load(getTableNameWithPrefix());
    }

    /**
     * Gets table name which loader will load data
     *
     * @return table name
     */
    protected abstract String getTableName();

    /**
     * Gets table name with minigame tables' prefix
     *
     * @return table name with minigame tables' prefix
     */
    protected String getTableNameWithPrefix() {
        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + getTableName();
    }
}
