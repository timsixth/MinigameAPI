package pl.timsixth.minigameapi.api.module.sql.core.loader;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.loader.AbstractLoader;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter;

/**
 * @see SqlDataBaseLoader
 * @see SQLDatabaseAdapter
 *
 * @param <T> every class which implemented {@link Model}
 */
@RequiredArgsConstructor
public abstract class AbstractSqlDataBaseLoader<T extends Model> extends AbstractLoader<T> implements SqlDataBaseLoader<T> {

    protected final SQLDatabaseAdapter sqlDatabaseAdapter;

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
