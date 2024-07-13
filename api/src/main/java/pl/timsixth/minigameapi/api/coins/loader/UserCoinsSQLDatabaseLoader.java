package pl.timsixth.minigameapi.api.coins.loader;

import pl.timsixth.minigameapi.api.coins.SQLDatabaseUserCoinsAdapter;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;

/**
 * @see AbstractSqlDataBaseLoader
 * @deprecated use SQL module
 */
@Deprecated
public class UserCoinsSQLDatabaseLoader extends AbstractSqlDataBaseLoader<UserCoins> implements UserCoinsLoader {

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
        return SQLDatabaseUserCoinsAdapter.TABLE_NAME;
    }
}
