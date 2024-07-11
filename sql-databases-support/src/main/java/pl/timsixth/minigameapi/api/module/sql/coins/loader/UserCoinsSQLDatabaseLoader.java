package pl.timsixth.minigameapi.api.module.sql.coins.loader;

import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;
import pl.timsixth.minigameapi.api.module.sql.coins.SQLDatabaseUserCoinsAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @see AbstractSqlDataBaseLoader
 */
public class UserCoinsSQLDatabaseLoader extends AbstractSqlDataBaseLoader<UserCoins> implements Loader<UserCoins> {

    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.selectAll(tableName).build();


    }

    @Override
    protected String getTableName() {
        return SQLDatabaseUserCoinsAdapter.TABLE_NAME;
    }
}
