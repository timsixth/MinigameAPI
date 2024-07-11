package pl.timsixth.minigameapi.api.stats.loader;

import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;
import pl.timsixth.minigameapi.api.stats.model.SQLDatabaseUserStatsAdapter;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

/**
 * Loads stats from database
 */
@Deprecated
public class UserStatsSQLDatabaseLoader extends AbstractSqlDataBaseLoader<UserStats> implements UserStatsLoader {

    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {

    }

    @Override
    protected String getTableName() {
        return SQLDatabaseUserStatsAdapter.TABLE_NAME;
    }
}
